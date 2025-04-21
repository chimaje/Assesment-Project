package uk.ac.leedsbeckett.finance.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findInvoiceByAccount_IdAndStatus(Long accountId, Status status);
    Invoice findInvoiceByReference(String reference);

    List<Invoice> findByAccount_StudentId(String studentId);
}
