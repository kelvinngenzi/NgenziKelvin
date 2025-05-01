package question03;

import java.time.LocalDate;

public class AUCAInternship extends Internship {

    public AUCAInternship(String internshipId, Student student, Company company,
                          Supervisor supervisor, LocalDate startDate, LocalDate endDate) {
        super(internshipId, student, company, supervisor, startDate, endDate);
    }

    @Override
    public void assignSupervisor() {
        System.out.println("Assigning AUCA supervisor: " + supervisor.getFullName() + " (with Community Service included)");
    }

    @Override
    public void trackProgress() {
        System.out.println("Tracking AUCA Internship progress for student: " + student.getFullName() + " (Weekly reports required)");
    }

    @Override
    public void generateReport() {
        System.out.println("------ AUCA Internship Report ------");
        System.out.println("Student: " + student.getFullName() + " (" + student.getUniversity() + ")");
        System.out.println("Internship ID: " + internshipId);
        System.out.println("Company: " + companyName + ", Industry: " + company.getIndustryType());
        System.out.println("Supervisor: " + supervisor.getFullName() + " [" + supervisor.getQualification() + "]");
        System.out.println("Duration: " + startDate + " to " + endDate);
        System.out.println("Status: " + status);
        System.out.println("Community Service Hours: Mandatory");
        System.out.println("----------------------------------");
    }

    @Override
    public boolean validateInternship() {
        boolean isAUCA = student.getUniversity().equalsIgnoreCase("AUCA");

        if (!isAUCA) {
            System.out.println("Validation Failed: Student must be from AUCA.");
            return false;
        }

        return true;
    }
}
