package question02;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class RRATaxSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<TaxDeclaration> declarations = new ArrayList<>();
    private static final List<Taxpayer> taxpayers = new ArrayList<>();
    private static final List<TaxOfficer> officers = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Welcome to RRA Tax Enforcement Management System");
        do{
            // Ask if user wants to add initial data
            System.out.print("Do you want to add initial taxpayers and officers? (y/n): ");
            String input= scanner.next().toLowerCase();
            if (input.startsWith("y")) {
                scanner.nextLine(); // Clear buffer
                initializeData();
                break;
            }
            else if (input.startsWith("n")) {
                break;
            }
             else {
            System.out.println("enter valid input!");

            }
        }while(true);

        scanner.nextLine(); // Clear buffer

        // Main application loop
        boolean exit = false;
        while (!exit) {
            System.out.println("\n===== RRA TAX ENFORCEMENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Register New Taxpayer");
            System.out.println("2. Register New Tax Officer");
            System.out.println("3. Declare PAYE Tax");
            System.out.println("4. Declare VAT");
            System.out.println("5. Declare Withholding Tax");
            System.out.println("6. View Declarations");
            System.out.println("7. Generate Taxpayer Compliance Report");
            System.out.println("8. Generate Tax Receipt");
            System.out.println("9. View Unpaid Taxes Summary");
            System.out.println("10. Audit Declaration");
            System.out.println("11. Exit");
            System.out.print("Enter your choice: ");

            int choice = getValidIntInput();

            switch (choice) {
                case 1:
                    registerTaxpayer();
                    break;
                case 2:
                    registerTaxOfficer();
                    break;
                case 3:
                    declarePAYE();
                    break;
                case 4:
                    declareVAT();
                    break;
                case 5:
                    declareWithholdingTax();
                    break;
                case 6:
                    viewDeclarations();
                    break;
                case 7:
                    generateComplianceReport();
                    break;
                case 8:
                    generateTaxReceipt();
                    break;
                case 9:
                    viewUnpaidTaxesSummary();
                    break;
                case 10:
                    auditDeclaration();
                    break;
                case 11:
                    exit = true;
                    System.out.println("Thank you for using the RRA Tax Enforcement Management System.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void initializeData() {
        // Initialize system with user input
        System.out.println("\n===== INITIALIZE SYSTEM DATA =====");

        // Add taxpayers
        System.out.print("How many taxpayers do you want to add initially? ");
        int taxpayerCount = getValidIntInput();
        scanner.nextLine(); // Clear buffer

        for (int i = 0; i < taxpayerCount; i++) {
            System.out.println("\nTaxpayer #" + (i + 1));
            registerTaxpayer();
        }

        // Add tax officers
        System.out.print("\nHow many tax officers do you want to add initially? ");
        int officerCount = getValidIntInput();
        scanner.nextLine(); // Clear buffer

        for (int i = 0; i < officerCount; i++) {
            System.out.println("\nTax Officer #" + (i + 1));
            registerTaxOfficer();
        }

        System.out.println("\nSystem initialized successfully with " + taxpayers.size() +
                " taxpayers and " + officers.size() + " tax officers.");
    }

    private static void registerTaxpayer() {
        System.out.println("\n===== REGISTER NEW TAXPAYER =====");
        scanner.nextLine();

        try {
            System.out.print("Enter Taxpayer Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter TIN (9 digits): ");
            String tin = scanner.nextLine();
            System.out.print("Taxpayer Type (1 for Individual, 2 for Company): ");
            int typeChoice = getValidIntInput();
            scanner.nextLine(); // Clear buffer

            Taxpayer.Type type = (typeChoice == 1) ? Taxpayer.Type.INDIVIDUAL : Taxpayer.Type.COMPANY;

            Taxpayer taxpayer = new Taxpayer(tin, name, type);
            taxpayers.add(taxpayer);
            System.out.println("Taxpayer registered successfully!");

        } catch (Exception e) {
            System.out.println("Error registering taxpayer: " + e.getMessage());
        }
    }

    private static void registerTaxOfficer() {
        System.out.println("\n===== REGISTER NEW TAX OFFICER =====");
        scanner.nextLine();

        try {
            System.out.print("Enter Officer ID: ");
            String officerId = scanner.nextLine();

            System.out.print("Enter Full Name: ");
            String fullName = scanner.nextLine();

            System.out.print("Enter Assigned Region: ");
            String region = scanner.nextLine();

            TaxOfficer officer = new TaxOfficer(officerId, fullName, region);
            officers.add(officer);
            System.out.println("Tax Officer registered successfully!");

        } catch (Exception e) {
            System.out.println("Error registering tax officer: " + e.getMessage());
        }
    }

    private static void declarePAYE() {
        System.out.println("\n===== PAYE TAX DECLARATION =====");

        // Get taxpayer details
        Taxpayer taxpayer = getTaxpayer();
        if (taxpayer == null) return;

        // Generate declaration ID
        String declarationId = "PAYE" + System.currentTimeMillis() % 10000;

        // Get declaration date
        LocalDate declarationDate = getDeclarationDate();
        if (declarationDate == null) return;

        // Get gross salary
        System.out.print("Enter gross salary: ");
        double grossSalary = getValidDoubleInput();
        scanner.nextLine(); // Clear buffer

        // Get payment status
        boolean isPaid = getPaymentStatus();
        scanner.nextLine(); // Clear buffer

        // Create PAYE declaration
        try {
            PAYEDeclaration paye = new PAYEDeclaration(declarationId, taxpayer.getName(), taxpayer.getTin(),
                    declarationDate, grossSalary, isPaid);

            if (paye.validateDeclaration()) {
                declarations.add(paye);
                taxpayer.addDeclaration(paye);
                System.out.println("PAYE declaration created successfully.");
                System.out.println("Tax amount: " + paye.getTaxAmount());

                // Generate receipt
                System.out.print("Generate receipt? (y/n): ");
                if (scanner.nextLine().toLowerCase().startsWith("y")) {
                    paye.generateReceipt();
                }
            } else {
                System.out.println("Declaration validation failed. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void declareVAT() {
        System.out.println("\n===== VAT DECLARATION =====");

        // Get taxpayer details
        Taxpayer taxpayer = getTaxpayer();
        if (taxpayer == null) return;

        // Generate declaration ID
        String declarationId = "VAT" + System.currentTimeMillis() % 10000;

        // Get declaration date
        LocalDate declarationDate = getDeclarationDate();
        if (declarationDate == null) return;

        // Get taxable sales and purchases
        System.out.print("Enter taxable sales: ");
        double taxableSales = getValidDoubleInput();
        scanner.nextLine(); // Clear buffer

        System.out.print("Enter taxable purchases: ");
        double taxablePurchases = getValidDoubleInput();
        scanner.nextLine(); // Clear buffer

        // Get payment status
        boolean isPaid = getPaymentStatus();
        scanner.nextLine(); // Clear buffer

        // Create VAT declaration
        try {
            VATDeclaration vat = new VATDeclaration(declarationId, taxpayer.getName(), taxpayer.getTin(),
                    declarationDate, taxableSales, taxablePurchases, isPaid);

            if (vat.validateDeclaration()) {
                declarations.add(vat);
                taxpayer.addDeclaration(vat);
                System.out.println("VAT declaration created successfully.");
                System.out.println("Tax amount: " + vat.getTaxAmount());

                // Generate receipt
                System.out.print("Generate receipt? (y/n): ");
                if (scanner.nextLine().toLowerCase().startsWith("y")) {
                    vat.generateReceipt();
                }
            } else {
                System.out.println("Declaration validation failed. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void declareWithholdingTax() {
        System.out.println("\n===== WITHHOLDING TAX DECLARATION =====");

        // Get taxpayer details
        Taxpayer taxpayer = getTaxpayer();
        if (taxpayer == null) return;

        // Generate declaration ID
        String declarationId = "WHT" + System.currentTimeMillis() % 10000;

        // Get declaration date
        LocalDate declarationDate = getDeclarationDate();
        if (declarationDate == null) return;

        // Get withholding tax category
        System.out.println("Select category:");
        System.out.println("1. Rent (10%)");
        System.out.println("2. Dividends (15%)");
        System.out.println("3. Services (15%)");
        System.out.print("Enter choice: ");
        int categoryChoice = getValidIntInput();
        scanner.nextLine(); // Clear buffer

        WithholdingTaxDeclaration.Category category;
        switch (categoryChoice) {
            case 1:
                category = WithholdingTaxDeclaration.Category.RENT;
                break;
            case 2:
                category = WithholdingTaxDeclaration.Category.DIVIDENDS;
                break;
            case 3:
                category = WithholdingTaxDeclaration.Category.SERVICES;
                break;
            default:
                System.out.println("Invalid category choice. Defaulting to Services.");
                category = WithholdingTaxDeclaration.Category.SERVICES;
        }

        // Get base amount
        System.out.print("Enter base amount: ");
        double baseAmount = getValidDoubleInput();
        scanner.nextLine(); // Clear buffer

        // Get payment status
        boolean isPaid = getPaymentStatus();
        scanner.nextLine(); // Clear buffer

        // Create Withholding Tax declaration
        try {
            WithholdingTaxDeclaration wht = new WithholdingTaxDeclaration(declarationId, taxpayer.getName(), taxpayer.getTin(),
                    declarationDate, category, baseAmount, isPaid);

            if (wht.validateDeclaration()) {
                declarations.add(wht);
                taxpayer.addDeclaration(wht);
                System.out.println("Withholding Tax declaration created successfully.");
                System.out.println("Tax amount: " + wht.getTaxAmount());

                // Generate receipt
                System.out.print("Generate receipt? (y/n): ");
                if (scanner.nextLine().toLowerCase().startsWith("y")) {
                    wht.generateReceipt();
                }
            } else {
                System.out.println("Declaration validation failed. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewDeclarations() {
        System.out.println("\n===== VIEW DECLARATIONS =====");

        if (declarations.isEmpty()) {
            System.out.println("No declarations found in the system.");
            return;
        }

        System.out.println("Total declarations: " + declarations.size());
        System.out.println("-----------------------------------");

        for (int i = 0; i < declarations.size(); i++) {
            TaxDeclaration declaration = declarations.get(i);
            String taxType = declaration.getClass().getSimpleName().replace("Declaration", "");
            System.out.printf("%d. %s | TIN: %s | Date: %s | Amount: %.2f | %s\n",
                    i + 1,
                    taxType,
                    declaration.getTaxpayerTIN(),
                    declaration.getDeclarationDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                    declaration.getTaxAmount(),
                    declaration.isPaid() ? "PAID" : "UNPAID");
        }
    }

    private static void generateComplianceReport() {
        System.out.println("\n===== GENERATE COMPLIANCE REPORT =====");

        // Get taxpayer
        Taxpayer taxpayer = getTaxpayer();
        if (taxpayer == null) return;

        // Generate and display compliance report
        String report = taxpayer.generateComplianceReport();
        System.out.println(report);
    }

    private static void generateTaxReceipt() {
        System.out.println("\n===== GENERATE TAX RECEIPT =====");

        if (declarations.isEmpty()) {
            System.out.println("No declarations found in the system.");
            return;
        }

        // Display declarations
        viewDeclarations();

        // Get declaration index
        System.out.print("Enter declaration number to generate receipt: ");
        int index = getValidIntInput() - 1;
        scanner.nextLine(); // Clear buffer

        if (index >= 0 && index < declarations.size()) {
            TaxDeclaration declaration = declarations.get(index);
            declaration.generateReceipt();
        } else {
            System.out.println("Invalid declaration number.");
        }
    }

    private static void viewUnpaidTaxesSummary() {
        System.out.println("\n===== UNPAID TAXES SUMMARY =====");

        List<TaxDeclaration> unpaidDeclarations = declarations.stream()
                .filter(d -> !d.isPaid())
                .collect(Collectors.toList());

        if (unpaidDeclarations.isEmpty()) {
            System.out.println("No unpaid declarations found.");
            return;
        }

        System.out.println("Total unpaid declarations: " + unpaidDeclarations.size());
        System.out.println("-----------------------------------");

        double totalUnpaid = 0;
        for (int i = 0; i < unpaidDeclarations.size(); i++) {
            TaxDeclaration declaration = unpaidDeclarations.get(i);
            String taxType = declaration.getClass().getSimpleName().replace("Declaration", "");
            System.out.printf("%d. %s | %s | Date: %s | Amount: %.2f\n",
                    i + 1,
                    taxType,
                    declaration.getTaxpayerName(),
                    declaration.getDeclarationDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                    declaration.getTaxAmount());

            totalUnpaid += declaration.getTaxAmount();
        }

        System.out.println("-----------------------------------");
        System.out.printf("Total Unpaid Amount: %.2f\n", totalUnpaid);
    }

    private static void auditDeclaration() {
        System.out.println("\n===== AUDIT DECLARATION =====");

        if (declarations.isEmpty()) {
            System.out.println("No declarations found in the system.");
            return;
        }

        if (officers.isEmpty()) {
            System.out.println("No tax officers registered in the system.");
            return;
        }

        // Select tax officer
        System.out.println("Select Tax Officer:");
        for (int i = 0; i < officers.size(); i++) {
            System.out.printf("%d. %s (%s)\n", i + 1, officers.get(i).getFullName(), officers.get(i).getOfficerId());
        }

        System.out.print("Enter officer number: ");
        int officerIndex = getValidIntInput() - 1;
        scanner.nextLine(); // Clear buffer

        if (officerIndex < 0 || officerIndex >= officers.size()) {
            System.out.println("Invalid officer number.");
            return;
        }

        TaxOfficer officer = officers.get(officerIndex);

        // Display declarations
        viewDeclarations();

        // Get declaration index
        System.out.print("Enter declaration number to audit: ");
        int declarationIndex = getValidIntInput() - 1;
        scanner.nextLine(); // Clear buffer

        if (declarationIndex >= 0 && declarationIndex < declarations.size()) {
            TaxDeclaration declaration = declarations.get(declarationIndex);
            officer.auditDeclaration(declaration);

            // Ask if user wants to see audit summary
            System.out.print("Generate audit summary? (y/n): ");
            if (scanner.nextLine().toLowerCase().startsWith("y")) {
                System.out.println(officer.generateAuditSummary());
            }
        } else {
            System.out.println("Invalid declaration number.");
        }
    }

    // Helper methods
    private static Taxpayer getTaxpayer() {
        if (taxpayers.isEmpty()) {
            System.out.println("No taxpayers registered in the system. Please register a taxpayer first.");
            return null;
        }

        System.out.println("Select Taxpayer:");
        for (int i = 0; i < taxpayers.size(); i++) {
            Taxpayer t = taxpayers.get(i);
            System.out.printf("%d. %s (TIN: %s, Type: %s)\n", i + 1, t.getName(), t.getTin(), t.getType());
        }

        System.out.print("Enter taxpayer number or 0 to register new: ");
        int index = getValidIntInput();
        scanner.nextLine(); // Clear buffer

        if (index == 0) {
            registerTaxpayer();
            return taxpayers.get(taxpayers.size() - 1);
        } else if (index > 0 && index <= taxpayers.size()) {
            return taxpayers.get(index - 1);
        } else {
            System.out.println("Invalid taxpayer number.");
            return null;
        }
    }

    private static LocalDate getDeclarationDate() {
        LocalDate today = LocalDate.now();

        System.out.println("Declaration date:");
        System.out.println("1. Today (" + today + ")");
        System.out.println("2. Custom date");
        System.out.print("Enter choice: ");

        int choice = getValidIntInput();
        scanner.nextLine(); // Clear buffer

        if (choice == 1) {
            return today;
        } else {
            try {
                System.out.print("Enter date (YYYY-MM-DD): ");
                String dateStr = scanner.nextLine();
                return LocalDate.parse(dateStr);
            } catch (Exception e) {
                System.out.println("Invalid date format. Using today's date.");
                return today;
            }
        }
    }

    private static boolean getPaymentStatus() {
        System.out.print("Is the tax paid? (y/n): ");
        return scanner.next().toLowerCase().startsWith("y");
    }

    private static int getValidIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static double getValidDoubleInput() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }
}