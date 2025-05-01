package question03;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class Internship {
    protected String internshipId;
    protected Student student;
    protected Company company;
    protected String companyName;
    protected Supervisor supervisor;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected String status; // "PENDING", "ONGOING", "COMPLETED"

    public Internship(String internshipId, Student student, Company company,
                      Supervisor supervisor, LocalDate startDate, LocalDate endDate) {
        this.internshipId = internshipId;
        this.student = student;
        this.company = company;
        this.companyName = company.getName();
        this.supervisor = supervisor;
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.status = "PENDING";
    }

    // Abstract methods to enforce in subclasses
    public abstract void assignSupervisor();

    public abstract void trackProgress();

    public abstract void generateReport();

    public abstract boolean validateInternship();

    // Common duration validation
    public boolean isMinimumDurationValid() {
        long weeks = ChronoUnit.WEEKS.between(startDate, endDate);
        return weeks >= 6;
    }

    // Getters and Setters
    public String getInternshipId() {
        return internshipId;
    }

    public Student getStudent() {
        return student;
    }

    public Company getCompany() {
        return company;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        if (startDate != null)
            this.startDate = startDate;
        else
            throw new IllegalArgumentException("Start date cannot be null");
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        if (endDate != null && endDate.isAfter(startDate)) {
            this.endDate = endDate;
        } else {
            throw new IllegalArgumentException("End date must be after start date");
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status.equals("PENDING") || status.equals("ONGOING") || status.equals("COMPLETED")) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Invalid internship status");
        }
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return this.companyName;
    }

}
