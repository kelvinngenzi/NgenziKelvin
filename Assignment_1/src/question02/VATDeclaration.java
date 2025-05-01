package question02;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class VATDeclaration extends TaxDeclaration {
    private double taxableSales;
    private double taxablePurchases;
    private static final double VAT_RATE = 0.18;  // 18% VAT

    public VATDeclaration(String declarationId, String taxpayerName, String taxpayerTIN,
                          LocalDate declarationDate, double taxableSales, double taxablePurchases, boolean isPaid) {
        super(declarationId, taxpayerName, taxpayerTIN, declarationDate, 0, isPaid);
        this.taxableSales = taxableSales;
        this.taxablePurchases = taxablePurchases;
        setTaxAmount(calculateTax());
    }

    @Override
    public double calculateTax() {
        double outputVAT = taxableSales * VAT_RATE;
        double inputVAT = taxablePurchases * VAT_RATE;
        return Math.max(0, outputVAT - inputVAT);
    }

    @Override
    public boolean validateDeclaration() {
        if (!validateTINAndDate()) {
            return false;
        }

        if (taxableSales < 0 || taxablePurchases < 0) {
            System.out.println("Invalid values: Sales and purchases cannot be negative");
            return false;
        }

        return true;
    }

    @Override
    public void generateReceipt() {
        System.out.println("\n======= RRA VAT RECEIPT =======");
        System.out.println("Declaration ID: " + getDeclarationId());
        System.out.println("Taxpayer: " + getTaxpayerName() + " (TIN: " + getTaxpayerTIN() + ")");
        System.out.println("Date: " + getDeclarationDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println("Taxable Sales: " + taxableSales);
        System.out.println("Taxable Purchases: " + taxablePurchases);
        System.out.println("VAT Rate: " + (VAT_RATE * 100) + "%");
        System.out.println("VAT Amount Due: " + getTaxAmount());
        System.out.println("Status: " + (isPaid() ? "PAID" : "UNPAID"));
        System.out.println("=================================");
    }

    @Override
    public double enforceCompliance() {
        double penalty = 0;

        if (!isPaid()) {
            // Standard 15% penalty for unpaid VAT
            penalty = getTaxAmount() * 0.15;
            System.out.println("VAT non-compliance penalty applied: " + penalty);
        }

        // Additional validation for mismatch detection
        if (taxablePurchases > taxableSales && taxableSales > 0) {
            System.out.println("ALERT: Purchases exceed sales - Flagged for review");
        }

        return penalty;
    }

    public double getTaxableSales() {
        return taxableSales;
    }

    public void setTaxableSales(double taxableSales) {
        this.taxableSales = taxableSales;
        setTaxAmount(calculateTax());
    }

    public double getTaxablePurchases() {
        return taxablePurchases;
    }

    public void setTaxablePurchases(double taxablePurchases) {
        this.taxablePurchases = taxablePurchases;
        setTaxAmount(calculateTax());
    }
}

