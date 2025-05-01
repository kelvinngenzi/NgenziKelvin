package question03;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class InternshipManager {
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Supervisor> supervisors = new ArrayList<>();
    private ArrayList<Company> companies = new ArrayList<>();
    private ArrayList<Internship> internships = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        InternshipManager manager = new InternshipManager();
        manager.start();  // Start the system
    }

    public void start() {
        while (true) {
            System.out.println("\n==== Internship Management System ====");
            System.out.println("1. Register Student");
            System.out.println("2. Register Supervisor");
            System.out.println("3. Register Company");
            System.out.println("4. Assign Internship");
            System.out.println("5. Search Internship by Student");
            System.out.println("6. Search Internship by University");
            System.out.println("7. View All Internships");
            System.out.println("8. Exit");
            System.out.print("Select an option: ");
            int choice = getIntInput();

            switch (choice) {
                case 1 -> registerStudent();
                case 2 -> registerSupervisor();
                case 3 -> registerCompany();
                case 4 -> assignInternship();
                case 5 -> searchByStudent();
                case 6 -> searchByUniversity();
                case 7 -> viewAllInternships();
                case 8 -> {
                    System.out.println("Exiting system. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void registerStudent() {
        System.out.println("\n-- Register Student --");
        System.out.print("Student ID: ");
        String id = scanner.nextLine();
        System.out.print("Full Name: ");
        String name = scanner.nextLine();
        System.out.print("University (ULK, UR, AUCA, UK): ");
        String university = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        try {
            Student student = new Student(id, name, university, email);
            students.add(student);
            System.out.println("Student registered successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to register student: " + e.getMessage());
        }
    }

    private void registerSupervisor() {
        System.out.println("\n-- Register Supervisor --");
        System.out.print("Supervisor ID: ");
        String id = scanner.nextLine();
        System.out.print("Full Name: ");
        String name = scanner.nextLine();
        System.out.print("Qualification (Bachelors, Masters, PhD): ");
        String qualification = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        try {
            Supervisor supervisor = new Supervisor(id, name, qualification, email);
            supervisors.add(supervisor);
            System.out.println("Supervisor registered successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to register supervisor: " + e.getMessage());
        }
    }

    private void registerCompany() {
        System.out.println("\n-- Register Company --");
        System.out.print("Company ID: ");
        String id = scanner.nextLine();
        System.out.print("Company Name: ");
        String name = scanner.nextLine();
        System.out.print("Industry Type (IT, Finance, Health, Education): ");
        String industryType = scanner.nextLine();
        System.out.print("Location: ");
        String location = scanner.nextLine();

        try {
            Company company = new Company(id, name, industryType, location);
            companies.add(company);
            System.out.println("Company registered successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to register company: " + e.getMessage());
        }
    }

    private void assignInternship() {
        System.out.println("\n-- Assign Internship --");
        System.out.print("Internship ID: ");
        String internshipId = scanner.nextLine();

        if (isDuplicateInternshipId(internshipId)) {
            System.out.println("Error: Internship ID already exists!");
            return;
        }

        Student student = findStudent();
        if (student == null) return;

        if (hasActiveInternship(student)) {
            System.out.println("Error: This student already has an active internship.");
            return;
        }

        Company company = findCompany();
        if (company == null) return;

        Supervisor supervisor = findSupervisor();
        if (supervisor == null) return;

        System.out.print("Start Date (YYYY-MM-DD): ");
        LocalDate startDate = getDateInput();
        System.out.print("End Date (YYYY-MM-DD): ");
        LocalDate endDate = getDateInput();

        if (!isValidDuration(startDate, endDate)) {
            System.out.println("Invalid dates: End date must be after start date and minimum 6 weeks.");
            return;
        }

        System.out.println("Select Internship Type:");
        System.out.println("1. ULK Internship");
        System.out.println("2. UR Internship");
        System.out.println("3. AUCA Internship");
        System.out.println("4. UK Internship");
        System.out.println("5. Remote Internship");
        System.out.print("Choice: ");
        int typeChoice = getIntInput();

        Internship internship = null;
        switch (typeChoice) {
            case 1 -> internship = new ULKInternship(internshipId, student, company, supervisor, startDate, endDate);
            case 2 -> internship = new URInternship(internshipId, student, company, supervisor, startDate, endDate);
            case 3 -> internship = new AUCAInternship(internshipId, student, company, supervisor, startDate, endDate);
            case 4 -> internship = new UKInternship(internshipId, student, company, supervisor, startDate, endDate);
            case 5 -> internship = new RemoteInternship(internshipId, student, company, supervisor, startDate, endDate);
            default -> {
                System.out.println("Invalid internship type selected.");
                return;
            }
        }

        internship.setStatus("PENDING");

        if (internship.validateInternship()) {
            internship.assignSupervisor();
            internships.add(internship);
            System.out.println("Internship assigned successfully!");
        } else {
            System.out.println("Failed to assign internship due to validation errors.");
        }
    }

    private void searchByStudent() {
        System.out.println("\n-- Search by Student Name --");
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().toLowerCase();

        boolean found = false;
        for (Internship i : internships) {
            if (i.getStudent().getFullName().toLowerCase().contains(name)) {
                i.generateReport();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No internships found for that student.");
        }
    }

    private void searchByUniversity() {
        System.out.println("\n-- Search by University --");
        System.out.print("Enter university name: ");
        String university = scanner.nextLine().toLowerCase();

        boolean found = false;
        for (Internship i : internships) {
            if (i.getStudent().getUniversity().toLowerCase().equals(university)) {
                i.generateReport();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No internships found for that university.");
        }
    }

    private void viewAllInternships() {
        System.out.println("\n-- All Internship Reports --");
        if (internships.isEmpty()) {
            System.out.println("No internships to display.");
            return;
        }
        for (Internship i : internships) {
            i.generateReport();
        }
    }

    // ============ Helper Methods ============

    private boolean isDuplicateInternshipId(String id) {
        for (Internship i : internships) {
            if (i.getInternshipId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasActiveInternship(Student student) {
        for (Internship i : internships) {
            if (i.getStudent().equals(student) &&
                    (i.getStatus().equalsIgnoreCase("PENDING") || i.getStatus().equalsIgnoreCase("ONGOING"))) {
                return true;
            }
        }
        return false;
    }

    private Student findStudent() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        for (Student s : students) {
            if (s.getStudentId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        System.out.println("Student not found.");
        return null;
    }

    private Supervisor findSupervisor() {
        System.out.print("Enter Supervisor ID: ");
        String id = scanner.nextLine();
        for (Supervisor s : supervisors) {
            if (s.getSupervisorId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        System.out.println("Supervisor not found.");
        return null;
    }

    private Company findCompany() {
        System.out.print("Enter Company ID: ");
        String id = scanner.nextLine();
        for (Company c : companies) {
            if (c.getCompanyId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        System.out.println("Company not found.");
        return null;
    }

    private boolean isValidDuration(LocalDate start, LocalDate end) {
        if (end.isBefore(start)) return false;
        long weeks = java.time.temporal.ChronoUnit.WEEKS.between(start, end);
        return weeks >= 6;
    }

    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input, enter a number: ");
            }
        }
    }

    private LocalDate getDateInput() {
        while (true) {
            try {
                return LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.print("Invalid date format. Please enter as YYYY-MM-DD: ");
            }
        }
    }
}
