package question03;

import java.time.LocalDate;

public class UKInternship extends Internship {

    public UKInternship(String internshipId, Student student, Company company,
                        Supervisor supervisor, LocalDate startDate, LocalDate endDate) {
        super(internshipId, student, company, supervisor, startDate, endDate);
    }

    @Override
    public void assignSupervisor() {
        System.out.println("Assigning UK supervisor: " + supervisor.getFullName() + " (Two supervisors required - Company and University)");
    }

    @Override
    public void trackProgress() {
        System.out.println("Tracking UK Internship progress for student: " + student.getFullName() + " (Bi-weekly reports required)");
    }

    @Override
    public void generateReport() {
        System.out.println("------ UK Internship Report ------");
        System.out.println("Student: " + student.getFullName() + " (" + student.getUniversity() + ")");
        System.out.println("Internship ID: " + internshipId);
        System.out.println("Company: " + companyName + ", Industry: " + company.getIndustryType());
        System.out.println("Supervisor: " + supervisor.getFullName() + " [" + supervisor.getQualification() + "]");
        System.out.println("Duration: " + startDate + " to " + endDate);
        System.out.println("Status: " + status);
        System.out.println("English Proficiency: Required");
        System.out.println("----------------------------------");
    }

    @Override
    public boolean validateInternship() {
        boolean isUK = student.getUniversity().equalsIgnoreCase("UK");

        if (!isUK) {
            System.out.println("Validation Failed: Student must be from UK.");
            return false;
        }

        return true;
    }
}
