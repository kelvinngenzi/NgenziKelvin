package question02;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Taxpayer {
    private String tin;
    private String name;
    private Type type;
    private int complianceScore;
    private List<TaxDeclaration> declarations;

    public enum Type {
        INDIVIDUAL, COMPANY
    }

    public Taxpayer(String tin, String name, Type type) {
        setTin(tin);
        setName(name);
        this.type = type;
        this.complianceScore = 100; // Start with perfect compliance
        this.declarations = new ArrayList<>();
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        if (tin == null || tin.length() != 9 || !tin.matches("\\d{9}")) {
            throw new IllegalArgumentException("TIN must be 9 digits");
        }
        this.tin = tin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        // Additional validation can be added for name format
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getComplianceScore() {
        return complianceScore;
    }

    public void setComplianceScore(int complianceScore) {
        if (complianceScore < 0) {
            this.complianceScore = 0;
        } else if (complianceScore > 100) {
            this.complianceScore = 100;
        } else {
            this.complianceScore = complianceScore;
        }
    }

    public void addDeclaration(TaxDeclaration declaration) {
        // Check for duplicate declarations in the same period
        for (TaxDeclaration existingDeclaration : declarations) {
            if (existingDeclaration.getDeclarationDate().equals(declaration.getDeclarationDate()) &&
                    existingDeclaration.getClass().equals(declaration.getClass())) {
                throw new IllegalArgumentException("Duplicate declaration for the same period and tax type");
            }
        }

        declarations.add(declaration);

        // Update compliance score based on declaration validation
        if (!declaration.validateDeclaration()) {
            decreaseComplianceScore(5);
        }

        // Update compliance score based on payment status
        if (!declaration.isPaid()) {
            decreaseComplianceScore(10);
        }
    }

    public List<TaxDeclaration> getDeclarations() {
        return new ArrayList<>(declarations); // Return a copy to maintain encapsulation
    }

    public void decreaseComplianceScore(int points) {
        setComplianceScore(complianceScore - points);
    }

    public void increaseComplianceScore(int points) {
        setComplianceScore(complianceScore + points);
    }

    public String generateComplianceReport() {
        StringBuilder report = new StringBuilder();
        report.append("\n=========== TAXPAYER COMPLIANCE REPORT ===========\n");
        report.append("Taxpayer: ").append(name).append("\n");
        report.append("TIN: ").append(tin).append("\n");
        report.append("Type: ").append(type).append("\n");
        report.append("Compliance Score: ").append(complianceScore).append("\n");
        report.append("--------------------------------------------------\n");
        report.append("DECLARATIONS:\n");

        double totalTax = 0;
        double totalPenalties = 0;

        if (declarations.isEmpty()) {
            report.append("No declarations found.\n");
        } else {
            for (TaxDeclaration declaration : declarations) {
                String taxType = declaration.getClass().getSimpleName().replace("Declaration", "");
                report.append(String.format("- %s | Date: %s | Amount: %.2f | Status: %s\n",
                        taxType,
                        declaration.getDeclarationDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                        declaration.getTaxAmount(),
                        declaration.isPaid() ? "PAID" : "UNPAID"));

                totalTax += declaration.getTaxAmount();
                double penalty = declaration.enforceCompliance();
                totalPenalties += penalty;

                if (penalty > 0) {
                    report.append(String.format("  ** Penalty Applied: %.2f **\n", penalty));
                }
            }
        }

        report.append("--------------------------------------------------\n");
        report.append(String.format("Total Tax Due: %.2f\n", totalTax));
        report.append(String.format("Total Penalties: %.2f\n", totalPenalties));
        report.append(String.format("Total Amount Payable: %.2f\n", totalTax + totalPenalties));
        report.append("==================================================\n");

        return report.toString();
    }
}
