package question01;

// Concrete Class 4: Bus
class Bus extends Vehicle {
    private int passengerCapacity;

    public Bus(String vehicleId, String ownerName, int yearOfFabrication,
               String registrationNumber, double baseTaxRate, int passengerCapacity) {
        super(vehicleId, ownerName, yearOfFabrication, registrationNumber, baseTaxRate, "Bus");
        setPassengerCapacity(passengerCapacity);
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        if (passengerCapacity <= 0) {
            throw new IllegalArgumentException("Passenger capacity must be greater than 0");
        }
        this.passengerCapacity = passengerCapacity;
    }

    @Override
    public double calculateTax() {
        double tax = baseTaxRate;

        // Tax increases by 2% per every 10 passengers
        double passengerIncrease = (passengerCapacity / 10) * 0.02;
        tax *= (1 + passengerIncrease);

        // Age > 20 years → increase tax by 10%
        if (calculateAge() > 20) {
            tax *= 1.1; // 10% increase
        }

        return tax;
    }

    @Override
    public void generateTaxReport() {
        System.out.println("\n=== BUS TAX REPORT ===");
        System.out.println(toString());
        System.out.println("Vehicle Age: " + calculateAge() + " years");
        System.out.println("Applied Adjustments:");

        double passengerIncrease = (passengerCapacity / 10) * 0.02;
        if (passengerIncrease > 0) {
            System.out.println("- Passenger Capacity Increase (" +
                    (int)(passengerIncrease * 100) + "%)");
        }

        if (calculateAge() > 20) {
            System.out.println("- Older Vehicle Surcharge (10%)");
        }

        System.out.println("Final Tax Amount: $" + calculateTax());
        System.out.println("====================");
    }

    @Override
    public String toString() {
        return super.toString() + "\nPassenger Capacity: " + passengerCapacity;
    }
}