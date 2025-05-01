package question01;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Vehicle> vehicles = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;

        System.out.println("Welcome to the Vehicle Tax Management System");

        while (!exit) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. Register a new vehicle");
            System.out.println("2. View registered vehicles");
            System.out.println("3. Calculate tax for all vehicles");
            System.out.println("4. Generate tax reports");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        registerNewVehicle();
                        break;
                    case 2:
                        viewRegisteredVehicles();
                        break;
                    case 3:
                        calculateTaxForAllVehicles();
                        break;
                    case 4:
                        generateTaxReports();
                        break;
                    case 5:
                        exit = true;
                        System.out.println("Thank you for using the Vehicle Tax Management System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void registerNewVehicle() {
        System.out.println("\n=== Register a New Vehicle ===");
        System.out.println("Please select vehicle type:");
        System.out.println("1. Car");
        System.out.println("2. Truck");
        System.out.println("3. Motorcycle");
        System.out.println("4. Bus");
        System.out.println("5. SUV");
        System.out.print("Enter your choice (1-5): ");

        try {
            int vehicleTypeChoice = Integer.parseInt(scanner.nextLine());
            if (vehicleTypeChoice < 1 || vehicleTypeChoice > 5) {
                System.out.println("Invalid vehicle type. Please try again.");
                return;
            }

            // Collect common vehicle information
            System.out.print("Enter Vehicle ID: ");
            String vehicleId = scanner.nextLine();
            checkDuplicateId(vehicleId);

            System.out.print("Enter Owner Name: ");
            String ownerName = scanner.nextLine();
            if (ownerName.trim().isEmpty()) {
                throw new IllegalArgumentException("Owner name cannot be empty.");
            }

            int yearOfFabrication =0;
            boolean validYear = false;
            do{
                try{
                    System.out.print("Enter Year of Fabrication: ");
                    yearOfFabrication = Integer.parseInt(scanner.nextLine());
                    if (yearOfFabrication < 1900) {
                        System.out.println("Registration error: Year of fabrication must be after 1900");
                        continue;
                    }

                    int currentYear = 2025; // Same as in Vehicle class
                    if (yearOfFabrication > currentYear) {
                        System.out.println("Registration error: Year of fabrication cannot be in the future");
                        continue;
                    }

                    validYear = true;
                }
                catch (NumberFormatException e){
                    System.out.println("Invalid input. Please enter a numeric year.");
                }

            } while(!validYear);


            System.out.print("Enter Registration Number: ");
            String registrationNumber = scanner.nextLine();
            checkDuplicateRegistration(registrationNumber);

            System.out.print("Enter Base Tax Rate ($): ");
            double baseTaxRate = Double.parseDouble(scanner.nextLine());
            if (baseTaxRate < 0) {
                throw new IllegalArgumentException("Base tax rate cannot be negative.");
            }

            // Create specific vehicle based on type
            Vehicle newVehicle = null;

            switch (vehicleTypeChoice) {
                case 1: // Car
                    System.out.print("Is the car electric? (true/false): ");
                    boolean isElectric = Boolean.parseBoolean(scanner.nextLine());
                    newVehicle = new Car(vehicleId, ownerName, yearOfFabrication,
                            registrationNumber, baseTaxRate, isElectric);
                    break;

                case 2: // Truck
                    System.out.print("Enter Load Capacity (tons): ");
                    double loadCapacity = Double.parseDouble(scanner.nextLine());
                    newVehicle = new Truck(vehicleId, ownerName, yearOfFabrication,
                            registrationNumber, baseTaxRate, loadCapacity);
                    break;

                case 3: // Motorcycle
                    System.out.print("Enter Engine Capacity (cc): ");
                    int engineCapacity = Integer.parseInt(scanner.nextLine());
                    newVehicle = new Motorcycle(vehicleId, ownerName, yearOfFabrication,
                            registrationNumber, baseTaxRate, engineCapacity);
                    break;

                case 4: // Bus
                    System.out.print("Enter Passenger Capacity: ");
                    int passengerCapacity = Integer.parseInt(scanner.nextLine());
                    newVehicle = new Bus(vehicleId, ownerName, yearOfFabrication,
                            registrationNumber, baseTaxRate, passengerCapacity);
                    break;

                case 5: // SUV
                    System.out.print("Does the SUV have four-wheel drive? (true/false): ");
                    boolean fourWheelDrive = Boolean.parseBoolean(scanner.nextLine());
                    newVehicle = new SUV(vehicleId, ownerName, yearOfFabrication,
                            registrationNumber, baseTaxRate, fourWheelDrive);
                    break;
            }

            vehicles.add(newVehicle);
            System.out.println("Vehicle registered successfully!");
            System.out.println("\nVehicle Details:");
            System.out.println(newVehicle);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input format. Please enter numeric values where required.");
        } catch (IllegalArgumentException e) {
            System.out.println("Registration error: " + e.getMessage());
        }
        scanner.close();
    }

    private static void checkDuplicateId(String vehicleId) {
        if (vehicleId == null || vehicleId.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle ID cannot be empty.");
        }

        for (Vehicle v : vehicles) {
            if (v.getVehicleId().equals(vehicleId)) {
                throw new IllegalArgumentException("Vehicle ID already exists. Please use a unique ID.");
            }
        }
    }

    private static void checkDuplicateRegistration(String registrationNumber) {
        if (registrationNumber == null || registrationNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Registration number cannot be empty.");
        }

        for (Vehicle v : vehicles) {
            if (v.getRegistrationNumber().equals(registrationNumber)) {
                throw new IllegalArgumentException("Registration number already exists. Please use a unique number.");
            }
        }
    }

    private static void viewRegisteredVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles registered yet.");
            return;
        }

        System.out.println("\n=== Registered Vehicles ===");
        int count = 1;
        for (Vehicle vehicle : vehicles) {
            System.out.println("\nVehicle #" + count++);
            System.out.println(vehicle);
            System.out.println("-------------------------");
        }
    }

    private static void calculateTaxForAllVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles registered yet.");
            return;
        }

        System.out.println("\n=== Tax Calculation Summary ===");
        double totalTax = 0;

        System.out.printf("%-15s %-15s %-15s %-15s %-15s%n",
                "Vehicle ID", "Type", "Owner", "Reg. Number", "Tax Amount");
        System.out.println("--------------------------------------------------------------------------------");

        for (Vehicle vehicle : vehicles) {
            double tax = vehicle.calculateTax();
            totalTax += tax;
            System.out.printf("%-15s %-15s %-15s %-15s $%-14.2f%n",
                    vehicle.getVehicleId(),
                    vehicle.getVehicleType(),
                    vehicle.getOwnerName(),
                    vehicle.getRegistrationNumber(),
                    tax);
        }

        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-62s $%-14.2f%n", "Total Tax Revenue:", totalTax);
    }

    private static void generateTaxReports() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles registered yet.");
            return;
        }

        System.out.println("\n=== Generating Tax Reports ===");

        for (Vehicle vehicle : vehicles) {
            vehicle.generateTaxReport();
        }
    }
}