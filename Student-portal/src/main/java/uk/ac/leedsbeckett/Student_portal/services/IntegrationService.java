package uk.ac.leedsbeckett.Student_portal.services;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.ac.leedsbeckett.Student_portal.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class IntegrationService {
    private final RestTemplate restTemplate;

    // Constructor injection for RestTemplate
    public IntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Method to register the student
    public void registerStudent(Student student) {
        String libraryUrl = "http://localhost/api/register";
        String financeUrl = "http://localhost:8081/accounts/register";


        // Create the request object with student data
        LibraryAccountRequest libraryAccountRequest = new LibraryAccountRequest(student.getExternalStudentId());
        FinanceAccountRequest financeAccountRequest = new FinanceAccountRequest(student.getExternalStudentId());
        // Wrap the request object in HttpEntity to send it with HTTP request
        HttpEntity<LibraryAccountRequest> requestEntity = new HttpEntity<>(libraryAccountRequest);
        HttpEntity<FinanceAccountRequest> requestEntity2 = new HttpEntity<>(financeAccountRequest);

        // Make the HTTP POST request
        ResponseEntity<Student> response = restTemplate.exchange(
                libraryUrl,
                HttpMethod.POST,
                requestEntity,
                Student.class
        );
        ResponseEntity<Student> response2 = restTemplate.exchange(
                financeUrl,
                HttpMethod.POST,
                requestEntity2,
                Student.class
        );

        // Check if the response status is successful and print corresponding message
        if (response.getStatusCode().is2xxSuccessful() && response2.getStatusCode().is2xxSuccessful()) {
            System.out.println("Student registered successfully");
        } else {
            // Check which response is not successful and print the corresponding error
            if (!response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Error registering student: " + response.getStatusCode());
            }
            if (!response2.getStatusCode().is2xxSuccessful()) {
                System.out.println("Error registering student: " + response2.getStatusCode());
            }
        }
    }

    // LibraryAccountRequest class with JSON serialization annotations
    public static class LibraryAccountRequest {

        @JsonProperty("studentId")  // Make sure to annotate the field for JSON serialization
        private String studentId;

        // Constructor to initialize the request object with student ID
        public LibraryAccountRequest(String studentId) {
            this.studentId = studentId;
        }

        // Getter for studentId (optional, but often needed for serialization)
        public String getStudentId() {
            return studentId;
        }

        // Setter for studentId (optional)
        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }
    }

    public static class FinanceAccountRequest {

        @JsonProperty("studentId")  // Make sure to annotate the field for JSON serialization
        private String studentId;

        // Constructor to initialize the request object with student ID
        public FinanceAccountRequest(String studentId) {
            this.studentId = studentId;
        }

        // Getter for studentId (optional, but often needed for serialization)
        public String getStudentId() {
            return studentId;
        }

        // Setter for studentId (optional)
        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }
    }

    public static class InvoiceRequest {
        @JsonProperty("studentId")
        private String studentId;

        @JsonProperty("amount")
        private double amount;

        // Default constructor (required for JSON deserialization)
        public InvoiceRequest() {
        }

        // All-args constructor
        public InvoiceRequest(String studentId, double amount) {
            this.studentId = studentId;
            this.amount = amount;
        }

        // Getters and Setters
        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        // Optional: toString() for logging
        @Override
        public String toString() {
            return "InvoiceRequest{" +
                    "studentId='" + studentId + '\'' +
                    ", amount=" + amount +
                    '}';
        }

        public Object get(String reference) {
            return reference;
        }
    }

    public static class InvoiceResponse {
        private String reference;
        private String status;
        public InvoiceResponse(String reference, String status) {
            this.reference = reference;
            this.status = status;
        }
        public String getReference() {
            return reference;
        }
    }

    public String createInvoice(String studentId, double amount) {
        String invoiceUrl = "http://localhost:8081/api/invoices/actions/course_fee";
        InvoiceRequest invoiceRequest = new InvoiceRequest(studentId, amount);
        HttpEntity<InvoiceRequest> requestEntity = new HttpEntity<>(invoiceRequest);
//        ResponseEntity<InvoiceRequest> response = restTemplate.exchange(
//                invoiceUrl,
//                HttpMethod.POST,
//                requestEntity,
//                InvoiceRequest.class
//        );
        ResponseEntity<InvoiceResponse> response=restTemplate.postForEntity(invoiceUrl, requestEntity,InvoiceResponse.class);
            System.out.println(response.getBody());
        // 5. Handle response
        if (response.getStatusCode().is2xxSuccessful()) {
            // Get the reference from the response body map
            return Objects.requireNonNull(response.getBody()).getReference();
        } else {
            throw new RuntimeException("Invoice creation failed: " +
                    response.getStatusCode());
        }
    }
}