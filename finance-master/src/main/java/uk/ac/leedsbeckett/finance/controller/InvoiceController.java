package uk.ac.leedsbeckett.finance.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.boot.json.JsonParseException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uk.ac.leedsbeckett.finance.model.Invoice;
import uk.ac.leedsbeckett.finance.model.InvoiceRequest;
import uk.ac.leedsbeckett.finance.service.InvoiceService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value ="/api/invoices",method = RequestMethod.POST,headers = "Accept=application/json")
public class InvoiceController {

    private final InvoiceService invoiceService;

    InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }
    @PostMapping("/actions/course_fee")
    public ResponseEntity<?> createCourseFeeInvoice(@Valid @RequestBody CourseFeeRequest request) {
        Invoice invoice = invoiceService.createCourseFeeInvoice(
                request.getStudentId(),
                request.getAmount()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                        "status", "Enrolled",
                        "reference", invoice.getReference(),
                        "studentId", invoice.getStudentId()
                )
        );

//        return ResponseEntity.status(HttpStatus.CREATED).body(invoice);
    }

    @GetMapping
    public CollectionModel<EntityModel<Invoice>> all() {
        return invoiceService.getAllInvoices();
    }


    @GetMapping("/{id}")
    public EntityModel<Invoice> one(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id);
    }

    @GetMapping("/reference/{reference}")
    public EntityModel<Invoice> one(@PathVariable String reference) {
        return invoiceService.getInvoiceByReference(reference);
    }

    @PostMapping("/register")
    public ResponseEntity<?> newInvoice(@RequestBody Invoice invoice) {
        return invoiceService.createNewInvoice(invoice);
    }

//    @PostMapping("/course_fee")
//    public ResponseEntity<?> createCourseFeeInvoice(@Valid @RequestBody CourseFeeRequest request) {
//        Invoice invoice = invoiceService.createCourseFeeInvoice(
//                request.getStudentId(),
//                request.getAmount()
//        );
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(invoice);
//    }

    @DeleteMapping("/{reference}/cancel")
    public ResponseEntity<?> cancel(@PathVariable String reference) {
        return invoiceService.cancel(reference);
    }

    @PutMapping("/{reference}/pay")
    public ResponseEntity<?> pay(@PathVariable String reference) {
        return invoiceService.pay(reference);
    }
    public static class CourseFeeRequest {
        private String studentId;
        private Double amount;
        public String getStudentId() { return studentId; }
        public void setStudentId(String studentId) { this.studentId = studentId; }
        public Double getAmount() { return amount; }
        public void setAmount(Double amount) { this.amount = amount; }
    }
    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.badRequest().body(errors);
    }

    // Handle JSON parse errors
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleBadInput(HttpMessageNotReadableException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        Throwable cause = ex.getCause();

        if (cause instanceof JsonParseException) {
            errorResponse.put("error", "Malformed JSON request");
        } else if (cause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) cause;
            errorResponse.put("error",
                    "Invalid format for field '" + ife.getPath().get(0).getFieldName() + "'");
            errorResponse.put("expectedType", ife.getTargetType().getSimpleName());
        } else {
            errorResponse.put("error", "Invalid request body");
        }

        return ResponseEntity.badRequest().body(errorResponse);
    }

    // Handle number format issues
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Map<String, String>> handleNumberFormatException() {
        return ResponseEntity.badRequest()
                .body(Map.of("error", "Invalid number format"));
    }
}