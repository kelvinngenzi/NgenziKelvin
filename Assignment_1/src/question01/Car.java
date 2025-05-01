package question01;

class Car extends Vehicle {
    private boolean isElectric;

    public Car(String vehicleId, String ownerName, int yearOfFabrication,
               String registrationNumber, double baseTaxRate, boolean isElectric) {
        super(vehicleId, ownerName, yearOfFabrication, registrationNumber, baseTaxRate, "Car");
        this.isElectric = isElectric;
    }

    public boolean isElectric() {
        return isElectric;
    }

    public void setElectric(boolean isElectric) {
        this.isElectric = isElectric;
    }

    @Override
    public double calculateTax() {
        double tax = baseTaxRate;

        // If electric → 20% tax discount
        if (isElectric) {
            tax *= 0.8; // 20% discount
        }

        // If older than 10 years → reduce base tax by 10%
        if (calculateAge() > 10) {
            tax *= 0.9; // 10% reduction
        }

        return tax;
    }

    @Override
    public void generateTaxReport() {
        System.out.println("\n=== CAR TAX REPORT ===");
        System.out.println(toString());
        System.out.println("Electric Vehicle: " + (isElectric ? "Yes" : "No"));
        System.out.println("Vehicle Age: " + calculateAge() + " years");
        System.out.println("Applied Discounts:");
        if (isElectric) {
            System.out.println("- Electric Vehicle Discount (20%)");
        }
        if (calculateAge() > 10) {
            System.out.println("- Older Vehicle Discount (10%)");
        }
        System.out.println("Final Tax Amount: $" + calculateTax());
        System.out.println("=====================");
    }

    @Override
    public String toString() {
        return super.toString() + "\nElectric: " + (isElectric ? "Yes" : "No");
    }
}