package question03;
public class Student {
    private String studentId;
    private String fullName;
    private String university;
    private String email;

    public Student(String studentId, String fullName, String university, String email) {
        setStudentId(studentId);
        setFullName(fullName);
        setUniversity(university);
        setEmail(email);
    }

    public void setStudentId(String studentId) {
        if(studentId.trim().isEmpty()){
            throw new IllegalArgumentException("the student id can not be empty");
        }
        this.studentId = studentId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUniversity(String university) {
        if (!(university.equalsIgnoreCase("ULK") ||
                university.equalsIgnoreCase("UR") ||
                university.equalsIgnoreCase("AUCA") ||
                university.equalsIgnoreCase("UK"))) {
            throw new IllegalArgumentException("Invalid university name.");
        }
        this.university = university.toUpperCase();
    }


    public void setEmail(String email) {
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = email;
    }

    public String getStudentId() { return studentId; }
    public String getFullName() { return fullName; }
    public String getUniversity() { return university; }
    public String getEmail() { return email; }
}

