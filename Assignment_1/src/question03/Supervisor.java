package question03;

public class Supervisor {
    private String supervisorId;
    private String fullName;
    private String qualification;
    private String email;

    public Supervisor(String supervisorId, String fullName, String qualification, String email) {
        this.setSupervisorId(supervisorId);
        this.setFullName(fullName);
        this.setQualification(qualification);
        this.setEmail(email);
    }

    public String getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(String supervisorId) {
        if(supervisorId.trim().isEmpty()){
            throw new IllegalArgumentException("the supervisor id can not be empty");
        }
        this.supervisorId = supervisorId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        if (qualification.equalsIgnoreCase("Bachelors") ||
                qualification.equalsIgnoreCase("Masters") ||
                qualification.equalsIgnoreCase("PhD")) {
            this.qualification = qualification;
        } else {
            throw new IllegalArgumentException("Invalid qualification. Must be Bachelors, Masters, or PhD.");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email.contains("@")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email format.");
        }
    }
}

