package question01;

abstract class Vehicle {
    // Attributes with protected access for use by subclasses
    protected String vehicleId;
    protected String ownerName;
    protected int yearOfFabrication;
    protected String registrationNumber;
    protected double baseTaxRate;
    protected String vehicleType;

    // Constructor
    public Vehicle(String vehicleId, String ownerName, int yearOfFabrication,
                   String registrationNumber, double baseTaxRate, String vehicleType) {
        setVehicleId(vehicleId);
        setOwnerName(ownerName);
        setYearOfFabrication(yearOfFabrication);
        setRegistrationNumber(registrationNumber);
        setBaseTaxRate(baseTaxRate);
        setVehicleType(vehicleType);
    }

    // Abstract methods
    public abstract double calculateTax();
    public abstract void generateTaxReport();

    // Common method to validate year
    protected boolean validateYearOfFabrication(int year) {
        int currentYear = 2025; // Current year
        return year <= currentYear;
    }

    // toString method
    @Override
    public String toString() {
        return "Vehicle ID: " + vehicleId +
                "\nOwner Name: " + ownerName +
                "\nYear of Fabrication: " + yearOfFabrication +
                "\nRegistration Number: " + registrationNumber +
                "\nBase Tax Rate: $" + baseTaxRate +
                "\nVehicle Type: " + vehicleType;
    }

    // Getters and Setters with validation
    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        if (vehicleId == null || vehicleId.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle ID cannot be empty");
        }
        this.vehicleId = vehicleId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        if (ownerName == null || ownerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner name cannot be empty");
        }
        this.ownerName = ownerName;
    }

    public int getYearOfFabrication() {
        return yearOfFabrication;
    }

    public void setYearOfFabrication(int yearOfFabrication) {
        if (!validateYearOfFabrication(yearOfFabrication)) {
            throw new IllegalArgumentException("Year of fabrication cannot be in the future");
        }
        if (yearOfFabrication < 1900) {
            throw new IllegalArgumentException("Year of fabrication must be after 1900");
        }
        this.yearOfFabrication = yearOfFabrication;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        if (registrationNumber == null || registrationNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Registration number cannot be empty");
        }
        this.registrationNumber = registrationNumber;
    }

    public double getBaseTaxRate() {
        return baseTaxRate;
    }

    public void setBaseTaxRate(double baseTaxRate) {
        if (baseTaxRate < 0) {
            throw new IllegalArgumentException("Base tax rate cannot be negative");
        }
        this.baseTaxRate = baseTaxRate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        if (vehicleType == null || vehicleType.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle type cannot be empty");
        }
        this.vehicleType = vehicleType;
    }

    // Calculate vehicle age
    protected int calculateAge() {
        return 2025 - yearOfFabrication; // Using current year 2025
    }
}