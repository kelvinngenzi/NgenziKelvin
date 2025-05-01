package question02;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class PAYEDeclaration extends TaxDeclaration {
    private double grossSalary;
    private static final double[][] TAX_BRACKETS = {
            {0, 30000, 0},             // 0% for first 30,000
            {30001, 100000, 0.20},     // 20% from 30,001 to 100,000
            {100001, Double.MAX_VALUE, 0.30}  // 30% for above 100,000
    };

    public PAYEDeclaration(String declarationId, String taxpayerName, String taxpayerTIN,
                           LocalDate declarationDate, double grossSalary, boolean isPaid) {
        super(declarationId, taxpayerName, taxpayerTIN, declarationDate, 0, isPaid);
        this.grossSalary = grossSalary;
        setTaxAmount(calculateTax());
    }

    @Override
    public double calculateTax() {
        if (grossSalary <= 0) {
            return 0;
        }

        double tax = 0;
        for (double[] bracket : TAX_BRACKETS) {
            if (grossSalary > bracket[0]) {
                double taxableInBracket = Math.min(grossSalary, bracket[1]) - bracket[0];
                tax += taxableInBracket * bracket[2];
            }
        }

        return tax;
    }

    @Override
    public boolean validateDeclaration() {
        if (!validateTINAndDate()) {
            return false;
        }

        if (grossSalary <= 0) {
            System.out.println("Invalid gross salary: Must be greater than 0");
            return false;
        }

        return true;
    }

    @Override
    public void generateReceipt() {
        System.out.println("\n======= RRA PAYE TAX RECEIPT =======");
        System.out.println("Declaration ID: " + getDeclarationId());
        System.out.println("Taxpayer: " + getTaxpayerName() + " (TIN: " + getTaxpayerTIN() + ")");
        System.out.println("Date: " + getDeclarationDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println("Gross Salary: " + grossSalary);
        System.out.println("PAYE Tax Amount: " + getTaxAmount());
        System.out.println("Status: " + (isPaid() ? "PAID" : "UNPAID"));
        System.out.println("=================================");
    }

    @Override
    public double enforceCompliance() {
        double penalty = 0;

        if (!isPaid()) {
            LocalDate dueDate = getDeclarationDate().plusMonths(1).withDayOfMonth(15);

            if (LocalDate.now().isAfter(dueDate)) {
                // 10% penalty for late payment after the 15th of the following month
                penalty = getTaxAmount() * 0.10;
                System.out.println("Late payment penalty applied: " + penalty);
            }
        }

        return penalty;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
        setTaxAmount(calculateTax());
    }
}
