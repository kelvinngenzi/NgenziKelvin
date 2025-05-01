package question03;

import java.time.LocalDate;

public class RemoteInternship extends Internship {

    public RemoteInternship(String internshipId, Student student, Company company,
                            Supervisor supervisor, LocalDate startDate, LocalDate endDate) {
        super(internshipId, student, company, supervisor, startDate, endDate);
    }

    @Override
    public void assignSupervisor() {
        System.out.println("Assigning Remote supervisor: " + supervisor.getFullName() + " (Remote communication enabled)");
    }

    @Override
    public void trackProgress() {
        System.out.println("Tracking Remote Internship progress for student: " + student.getFullName() + " (Communication logs must be updated weekly)");
    }

    @Override
    public void generateReport() {
        System.out.println("------ Remote Internship Report ------");
        System.out.println("Student: " + student.getFullName() + " (" + student.getUniversity() + ")");
        System.out.println("Internship ID: " + internshipId);
        System.out.println("Company: " + companyName + ", Industry: " + company.getIndustryType());
        System.out.println("Supervisor: " + supervisor.getFullName() + " [" + supervisor.getQualification() + "]");
        System.out.println("Duration: " + startDate + " to " + endDate);
        System.out.println("Status: " + status);
        System.out.println("Remote Access: Enabled");
        System.out.println("----------------------------------");
    }

    @Override
    public boolean validateInternship() {
        // No restriction on university
        return true;
    }
}
