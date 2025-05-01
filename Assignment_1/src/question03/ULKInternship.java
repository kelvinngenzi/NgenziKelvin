package question03;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ULKInternship extends Internship {

    public ULKInternship(String internshipId, Student student, Company company,
                         Supervisor supervisor, LocalDate startDate, LocalDate endDate) {
        super(internshipId, student, company, supervisor, startDate, endDate);
    }

    @Override
    public void assignSupervisor() {
        if (!supervisor.getQualification().equalsIgnoreCase("Masters") &&
                !supervisor.getQualification().equalsIgnoreCase("PhD")) {
            throw new IllegalArgumentException("ULK supervisor must have a Master's degree or higher.");
        }
        else{
            System.out.println("Assigning ULK supervisor: " + supervisor.getFullName());
        }

    }

    @Override
    public void trackProgress() {
        System.out.println("Tracking ULK Internship progress for student: " + student.getFullName());
    }

    @Override
    public void generateReport() {
        System.out.println("------ ULK Internship Report ------");
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
        boolean isULK = student.getUniversity().equalsIgnoreCase("ULK");
        boolean isDurationValid = isMinimumDurationValid();
        boolean isQualifiedSupervisor =
                supervisor.getQualification().equalsIgnoreCase("Masters") ||
                        supervisor.getQualification().equalsIgnoreCase("PhD");

        if (!isULK) {
            System.out.println("Validation Failed: Student must be from ULK.");
            return false;
        }
        if (!isDurationValid) {
            System.out.println("Validation Failed: Internship must be at least 6 weeks.");
            return false;
        }
        if (!isQualifiedSupervisor) {
            System.out.println("Validation Failed: Supervisor must have a Master's or PhD.");
            return false;
        }

        return true;
    }
}
