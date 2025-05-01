package question01;

// Concrete Class 2: Truck
class Truck extends Vehicle {
    private double loadCapacity;

    public Truck(String vehicleId, String ownerName, int yearOfFabrication,
                 String registrationNumber, double baseTaxRate, double loadCapacity) {
        super(vehicleId, ownerName, yearOfFabrication, registrationNumber, baseTaxRate, "Truck");
        setLoadCapacity(loadCapacity);
    }

    public double getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(double loadCapacity) {
        if (loadCapacity <= 0) {
            throw new IllegalArgumentException("Load capacity must be greater than 0");
        }
        this.loadCapacity = loadCapacity;
    }

    @Override
    public double calculateTax() {
        double tax = baseTaxRate;

        // Trucks older than 15 years → 15% extra tax
        if (calculateAge() > 15) {
            tax *= 1.15; // 15% increase
        }

        // Load capacity > 10 tons → increase tax by 25%
        if (loadCapacity > 10) {
            tax *= 1.25; // 25% increase
        }

        return tax;
    }

    @Override
    public void generateTaxReport() {
        System.out.println("\n=== TRUCK TAX REPORT ===");
        System.out.println(toString());
        System.out.println("Vehicle Age: " + calculateAge() + " years");
        System.out.println("Applied Adjustments:");
        if (calculateAge() > 15) {
            System.out.println("- Older Truck Surcharge (15%)");
        }
        if (loadCapacity > 10) {
            System.out.println("- High Capacity Surcharge (25%)");
        }
        System.out.println("Final Tax Amount: $" + calculateTax());
        System.out.println("======================");
    }

    @Override
    public String toString() {
        return super.toString() + "\nLoad Capacity: " + loadCapacity + " tons";
    }
}