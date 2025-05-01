package question03;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class URInternship extends Internship {

    public URInternship(String internshipId, Student student, Company company,
                        Supervisor supervisor, LocalDate startDate, LocalDate endDate) {
        super(internshipId, student, company, supervisor, startDate, endDate);
    }

    @Override
    public void assignSupervisor() {
        System.out.println("Assigning UR supervisor: " + supervisor.getFullName() + " (Dual supervisors allowed optionally)");
    }

    @Override
    public void trackProgress() {
        System.out.println("Tracking UR Internship progress for student: " + student.getFullName());
    }

    @Override
    public void generateReport() {
        System.out.println("------ UR Internship Report ------");
        System.out.println("Student: " + student.getFullName() + " (" + student.getUniversity() + ")");
        System.out.println("Internship ID: " + internshipId);
        System.out.println("Company: " + companyName + ", Industry: " + company.getIndustryType());
        System.out.println("Supervisor: " + supervisor.getFullName() + " [" + supervisor.getQualification() + "]");
        System.out.println("Duration: " + startDate + " to " + endDate);
        System.out.println("Status: " + status);
        System.out.println("----------------------------------");
    }

    @Override
    public boolean validateInternship() {
        boolean isUR = student.getUniversity().equalsIgnoreCase("UR");
        boolean isDurationValid = isDurationBetween2And6Months();

        if (!isUR) {
            System.out.println("Validation Failed: Student must be from UR.");
            return false;
        }
        if (!isDurationValid) {
            System.out.println("Validation Failed: Internship duration must be between 2 and 6 months.");
            return false;
        }

        return true;
    }

    private boolean isDurationBetween2And6Months() {
        long months = ChronoUnit.MONTHS.between(startDate, endDate);
        return months >= 2 && months <= 6;
    }
}
