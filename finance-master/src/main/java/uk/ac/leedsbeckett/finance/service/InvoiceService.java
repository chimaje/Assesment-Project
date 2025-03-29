package uk.ac.leedsbeckett.finance.service;

import org.springframework.context.MessageSource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import uk.ac.leedsbeckett.finance.controller.InvoiceController;
import uk.ac.leedsbeckett.finance.exception.AccountNotFoundException;
import uk.ac.leedsbeckett.finance.exception.InvoiceNotFoundException;
import uk.ac.leedsbeckett.finance.exception.InvoiceNotValidException;
import uk.ac.leedsbeckett.finance.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class InvoiceService {

    private final AccountRepository accountRepository;
    private final InvoiceModelAssembler assembler;
    private final InvoiceRepository invoiceRepository;
    private final MessageSource messageSource;

    public InvoiceService(AccountRepository accountRepository, InvoiceModelAssembler assembler, InvoiceRepository invoiceRepository, MessageSource messageSource) {
        this.accountRepository = accountRepository;
        this.assembler = assembler;
        this.invoiceRepository = invoiceRepository;
        this.messageSource = messageSource;
    }

    // Method to get invoice by ID
    public EntityModel<Invoice> getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new InvoiceNotFoundException(id));
        return assembler.toModel(invoice);
    }

    // Method to get all invoices
    public CollectionModel<EntityModel<Invoice>> getAllInvoices() {
        List<EntityModel<Invoice>> invoices = invoiceRepository.findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(invoices, linkTo(methodOn(InvoiceController.class).all()).withSelfRel());
    }

    // Method to create a new invoice
    public ResponseEntity<?> createNewInvoice(Invoice invoice) {
        if (!isInvoiceProcessable(invoice)) {
            throw new InvoiceNotValidException("You can't create an invoice without a valid student ID.");
        }

        // Ensure that the invoice type is valid
        if (invoice.getType() != Type.TUITION_FEES && invoice.getType() != Type.LIBRARY_FINE) {
            throw new InvoiceNotValidException("The invoice type must be either TUITION_FEE or LIBRARY_FEE.");
        }

        invoice.setStatus(Status.OUTSTANDING);
        invoice.setAccount(accountRepository.findAccountByStudentId(invoice.getStudentId()));
        invoice.populateReference();
        Invoice newInvoice = invoiceRepository.save(invoice);

        return ResponseEntity
                .created(linkTo(methodOn(InvoiceController.class).one(newInvoice.getId())).toUri())
                .body(assembler.toModel(newInvoice));
    }

    // Method to cancel an invoice
    public ResponseEntity<?> cancel(String reference) {
        Invoice invoice = invoiceRepository.findInvoiceByReference(reference);

        if (invoice == null) {
            throw new InvoiceNotFoundException(reference);
        }

        if (invoice.getStatus() == Status.OUTSTANDING) {
            invoice.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(assembler.toModel(invoiceRepository.save(invoice)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't cancel an invoice that is in the " + invoice.getStatus() + " status"));
    }

    // Method to pay an invoice
    public ResponseEntity<?> pay(String reference) {
        Invoice invoice;
        try {
            invoice = processPayment(reference);
        } catch (UnsupportedOperationException exception) {
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Method not allowed")
                            .withDetail(exception.getMessage()));
        }
        return ResponseEntity.ok(assembler.toModel(invoiceRepository.save(invoice)));
    }

    // Method to get invoice by reference
    public EntityModel<Invoice> getInvoiceByReference(String reference) {
        Invoice invoice = invoiceRepository.findInvoiceByReference(reference);
        if (invoice == null) {
            throw new InvoiceNotFoundException(reference);
        }
        return assembler.toModel(invoice);
    }

    private boolean isInvoiceProcessable(Invoice invoice) {
        return invoice != null &&
                invoice.getAccount() != null &&
                invoice.getStudentId() != null &&
                !invoice.getStudentId().isEmpty() &&
                accountRepository.findAccountByStudentId(invoice.getStudentId()) != null;
    }

    // Method to process payment for an invoice
    public Invoice processPayment(String reference) throws UnsupportedOperationException {
        Invoice invoice = invoiceRepository.findInvoiceByReference(reference);

        if (invoice == null) {
            throw new InvoiceNotFoundException(reference);
        }

        if (invoice.getStatus() == Status.OUTSTANDING) {
            invoice.setStatus(Status.PAID);
            return invoiceRepository.save(invoice);
        } else {
            throw new UnsupportedOperationException("You can't pay an invoice that is in the " + invoice.getStatus() + " status");
        }
    }

    // Method to show the portal
    public String showPortal(Model model) {
        Invoice invoice = new Invoice();
        model.addAttribute("invoice", invoice);
        return "portal";
    }

    // Method to find an invoice through the portal
    public String findInvoiceThroughPortal(Invoice invoice, BindingResult bindingResult, Model model) {
        if (invoice == null || invoice.getReference() == null) {
            throw new InvoiceNotFoundException();
        }
        if (bindingResult.hasErrors()) {
            return "portal";
        }
        Invoice found = getInvoiceByReference(invoice.getReference()).getContent();
        model.addAttribute("invoice", found);
        return "invoice";
    }

    // Method to pay an invoice through the portal
    public String payInvoiceThroughPortal(Invoice invoice, Model model) {
        if (invoice == null || invoice.getReference() == null) {
            throw new InvoiceNotFoundException();
        }
        Invoice paidInvoice = processPayment(invoice.getReference());
        model.addAttribute("invoice", paidInvoice);
        model.addAttribute("message", messageSource.getMessage("invoice.paid", null, Locale.ROOT));
        return "invoice";
    }
    public Invoice createCourseFeeInvoice(String studentId, double courseFeeAmount) {
        Account account = accountRepository.findAccountByStudentId(studentId);

        if (account == null) {
            throw new AccountNotFoundException("Account for student ID " + studentId + " not found.");
        }

        Invoice invoice = new Invoice(courseFeeAmount, LocalDate.now().plusMonths(1), Type.COURSE_FEE, account);
        invoice.setStatus(Status.OUTSTANDING);
        invoice.populateReference();
        return invoiceRepository.save(invoice);
    }
}
