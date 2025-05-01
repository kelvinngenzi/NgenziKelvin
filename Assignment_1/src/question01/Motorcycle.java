package question01;

class Motorcycle extends Vehicle {
    private int engineCapacity;

    public Motorcycle(String vehicleId, String ownerName, int yearOfFabrication,
                      String registrationNumber, double baseTaxRate, int engineCapacity) {
        super(vehicleId, ownerName, yearOfFabrication, registrationNumber, baseTaxRate, "Motorcycle");
        setEngineCapacity(engineCapacity);
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(int engineCapacity) {
        if (engineCapacity <= 0) {
            throw new IllegalArgumentException("Engine capacity must be greater than 0");
        }
        this.engineCapacity = engineCapacity;
    }

    @Override
    public double calculateTax() {
        double tax = baseTaxRate;

        // Engine capacity > 500cc → 20% extra tax
        if (engineCapacity > 500) {
            tax *= 1.2; // 20% increase
        }

        // Age-based depreciation: 5% reduction every 5 years
        int ageReductions = calculateAge() / 5;
        double reductionFactor = 1 - (ageReductions * 0.05);
        tax *= reductionFactor;

        return tax;
    }

    @Override
    public void generateTaxReport() {
        System.out.println("\n=== MOTORCYCLE TAX REPORT ===");
        System.out.println(toString());
        System.out.println("Vehicle Age: " + calculateAge() + " years");
        System.out.println("Applied Adjustments:");
        if (engineCapacity > 500) {
            System.out.println("- High Engine Capacity Surcharge (20%)");
        }
        int ageReductions = calculateAge() / 5;
        if (ageReductions > 0) {
            System.out.println("- Age-based Depreciation (" + (ageReductions * 5) + "%)");
        }
        System.out.println("Final Tax Amount: $" + calculateTax());
        System.out.println("===========================");
    }

    @Override
    public String toString() {
        return super.toString() + "\nEngine Capacity: " + engineCapacity + "cc";
    }
}