package question02;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
abstract class TaxDeclaration implements TaxCalculable, Receiptable {
    private String declarationId;
    private String taxpayerName;
    private String taxpayerTIN;
    private LocalDate declarationDate;
    private double taxAmount;
    private boolean isPaid;

    public TaxDeclaration(String declarationId, String taxpayerName, String taxpayerTIN,
                          LocalDate declarationDate, double taxAmount, boolean isPaid) {
        this.declarationId = declarationId;
        this.taxpayerName = taxpayerName;
        this.taxpayerTIN = taxpayerTIN;
        this.declarationDate = declarationDate;
        this.taxAmount = taxAmount;
        this.isPaid = isPaid;
    }

    // Abstract methods
    public abstract double calculateTax();
    public abstract boolean validateDeclaration();
    public abstract void generateReceipt();
    public abstract double enforceCompliance();

    // Getters and setters
    public String getDeclarationId() {
        return declarationId;
    }

    public void setDeclarationId(String declarationId) {
        this.declarationId = declarationId;
    }

    public String getTaxpayerName() {
        return taxpayerName;
    }

    public void setTaxpayerName(String taxpayerName) {
        this.taxpayerName = taxpayerName;
    }

    public String getTaxpayerTIN() {
        return taxpayerTIN;
    }

    public void setTaxpayerTIN(String taxpayerTIN) {
        this.taxpayerTIN = taxpayerTIN;
    }

    public LocalDate getDeclarationDate() {
        return declarationDate;
    }

    public void setDeclarationDate(LocalDate declarationDate) {
        this.declarationDate = declarationDate;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    // Common validation method
    protected boolean validateTINAndDate() {
        // TIN must be 9 digits
        if (taxpayerTIN == null || taxpayerTIN.length() != 9 || !taxpayerTIN.matches("\\d{9}")) {
            System.out.println("Invalid TIN: Must be 9 digits");
            return false;
        }

        // Declaration date must not be in the future
        if (declarationDate.isAfter(LocalDate.now())) {
            System.out.println("Invalid declaration date: Cannot be in the future");
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return String.format("Declaration ID: %s, Taxpayer: %s (TIN: %s), Date: %s, Amount: %.2f, Paid: %s",
                declarationId, taxpayerName, taxpayerTIN,
                declarationDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
                taxAmount, isPaid ? "Yes" : "No");
    }
}