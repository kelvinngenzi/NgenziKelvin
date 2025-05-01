package question01;
class SUV extends Vehicle {
    private boolean fourWheelDrive;

    public SUV(String vehicleId, String ownerName, int yearOfFabrication,
               String registrationNumber, double baseTaxRate, boolean fourWheelDrive) {
        super(vehicleId, ownerName, yearOfFabrication, registrationNumber, baseTaxRate, "SUV");
        this.fourWheelDrive = fourWheelDrive;
    }

    public boolean isFourWheelDrive() {
        return fourWheelDrive;
    }

    public void setFourWheelDrive(boolean fourWheelDrive) {
        this.fourWheelDrive = fourWheelDrive;
    }

    @Override
    public double calculateTax() {
        double tax = baseTaxRate;

        // If 4WD → increase tax by 10%
        if (fourWheelDrive) {
            tax *= 1.1; // 10% increase
        }

        // Age > 10 years → decrease tax by 5%
        if (calculateAge() > 10) {
            tax *= 0.95; // 5% decrease
        }

        return tax;
    }

    @Override
    public void generateTaxReport() {
        System.out.println("\n=== SUV TAX REPORT ===");
        System.out.println(toString());
        System.out.println("Vehicle Age: " + calculateAge() + " years");
        System.out.println("Applied Adjustments:");

        if (fourWheelDrive) {
            System.out.println("- Four Wheel Drive Surcharge (10%)");
        }

        if (calculateAge() > 10) {
            System.out.println("- Older Vehicle Discount (5%)");
        }

        System.out.println("Final Tax Amount: $" + calculateTax());
        System.out.println("====================");
    }

    @Override
    public String toString() {
        return super.toString() + "\nFour Wheel Drive: " + (fourWheelDrive ? "Yes" : "No");
    }
}


