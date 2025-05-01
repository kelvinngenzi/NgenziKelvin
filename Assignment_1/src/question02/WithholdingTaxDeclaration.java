package question02;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class WithholdingTaxDeclaration extends TaxDeclaration {
    public enum Category {
        RENT(0.10),      // 10% withholding on rent
        DIVIDENDS(0.15), // 15% withholding on dividends
        SERVICES(0.15);  // 15% withholding on professional services

        private final double rate;

        Category(double rate) {
            this.rate = rate;
        }

        public double getRate() {
            return rate;
        }
    }

    private Category category;
    private double baseAmount;

    public WithholdingTaxDeclaration(String declarationId, String taxpayerName, String taxpayerTIN,
                                     LocalDate declarationDate, Category category, double baseAmount, boolean isPaid) {
        super(declarationId, taxpayerName, taxpayerTIN, declarationDate, 0, isPaid);
        this.category = category;
        this.baseAmount = baseAmount;
        setTaxAmount(calculateTax());
    }

    @Override
    public double calculateTax() {
        return baseAmount * category.getRate();
    }

    @Override
    public boolean validateDeclaration() {
        if (!validateTINAndDate()) {
            return false;
        }

        if (baseAmount <= 0) {
            System.out.println("Invalid base amount: Must be greater than 0");
            return false;
        }

        if (category == null) {
            System.out.println("Invalid category: Must specify RENT, DIVIDENDS, or SERVICES");
            return false;
        }

        return true;
    }

    @Override
    public void generateReceipt() {
        System.out.println("\n======= RRA WITHHOLDING TAX RECEIPT =======");
        System.out.println("Declaration ID: " + getDeclarationId());
        System.out.println("Taxpayer: " + getTaxpayerName() + " (TIN: " + getTaxpayerTIN() + ")");
        System.out.println("Date: " + getDeclarationDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println("Category: " + category);
        System.out.println("Base Amount: " + baseAmount);
        System.out.println("Rate: " + (category.getRate() * 100) + "%");
        System.out.println("Withholding Tax Amount: " + getTaxAmount());
        System.out.println("Status: " + (isPaid() ? "PAID" : "UNPAID"));
        System.out.println("=================================");
    }

    @Override
    public double enforceCompliance() {
        double penalty = 0;

        if (!isPaid()) {
            // 20% penalty for unpaid withholding tax
            penalty = getTaxAmount() * 0.20;
            System.out.println("Withholding tax non-compliance penalty applied: " + penalty);
            System.out.println("ALERT: Payment made without valid declaration - Flagged for audit");
        }

        return penalty;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
        setTaxAmount(calculateTax());
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(double baseAmount) {
        this.baseAmount = baseAmount;
        setTaxAmount(calculateTax());
    }
}