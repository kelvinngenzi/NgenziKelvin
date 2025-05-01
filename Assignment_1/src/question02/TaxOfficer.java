package question02;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class TaxOfficer {
    private String officerId;
    private String fullName;
    private String assignedRegion;
    private List<TaxDeclaration> auditsConducted;

    public TaxOfficer(String officerId, String fullName, String assignedRegion) {
        setOfficerId(officerId);
        setFullName(fullName);
        setAssignedRegion(assignedRegion);
        this.auditsConducted = new ArrayList<>();
    }

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        if (officerId == null || officerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Officer ID cannot be empty");
        }
        this.officerId = officerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        this.fullName = fullName;
    }

    public String getAssignedRegion() {
        return assignedRegion;
    }

    public void setAssignedRegion(String assignedRegion) {
        if (assignedRegion == null || assignedRegion.trim().isEmpty()) {
            throw new IllegalArgumentException("Assigned region cannot be empty");
        }
        this.assignedRegion = assignedRegion;
    }

    public List<TaxDeclaration> getAuditsConducted() {
        return new ArrayList<>(auditsConducted); // Return a copy to maintain encapsulation
    }

    public void auditDeclaration(TaxDeclaration declaration) {
        // Perform audit process
        System.out.println("\n======= TAX AUDIT REPORT =======");
        System.out.println("Auditor: " + fullName + " (ID: " + officerId + ")");
        System.out.println("Region: " + assignedRegion);
        System.out.println("Declaration ID: " + declaration.getDeclarationId());
        System.out.println("Taxpayer: " + declaration.getTaxpayerName() + " (TIN: " + declaration.getTaxpayerTIN() + ")");

        // Validate declaration
        boolean isValid = declaration.validateDeclaration();
        System.out.println("Declaration Validity: " + (isValid ? "VALID" : "INVALID"));

        // Check compliance
        double penalty = declaration.enforceCompliance();
        System.out.println("Compliance Penalty: " + penalty);

        // Record audit
        auditsConducted.add(declaration);
        System.out.println("Audit completed successfully.");
        System.out.println("================================");
    }

    public String generateAuditSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("\n=========== AUDIT SUMMARY ===========\n");
        summary.append("Tax Officer: ").append(fullName).append(" (ID: ").append(officerId).append(")\n");
        summary.append("Region: ").append(assignedRegion).append("\n");
        summary.append("Total Audits Conducted: ").append(auditsConducted.size()).append("\n");
        summary.append("----------------------------------\n");

        if (auditsConducted.isEmpty()) {
            summary.append("No audits conducted yet.\n");
        } else {
            for (int i = 0; i < auditsConducted.size(); i++) {
                TaxDeclaration declaration = auditsConducted.get(i);
                String taxType = declaration.getClass().getSimpleName().replace("Declaration", "");
                summary.append(String.format("%d. %s | TIN: %s | Date: %s | Amount: %.2f | Status: %s\n",
                        i + 1,
                        taxType,
                        declaration.getTaxpayerTIN(),
                        declaration.getDeclarationDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                        declaration.getTaxAmount(),
                        declaration.isPaid() ? "PAID" : "UNPAID"));
            }
        }

        summary.append("===================================\n");
        return summary.toString();
    }
}
