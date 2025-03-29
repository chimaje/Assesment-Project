package uk.ac.leedsbeckett.finance.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Setter
@Getter
public class InvoiceRequest {
    // Getters/Setters
    @NotBlank
    private String studentId;

    @Positive
    private double amount;

    // Constructors
    public InvoiceRequest() {}

    public InvoiceRequest(String studentId, double amount) {
        this.studentId = studentId;
        this.amount = amount;
    }

}
