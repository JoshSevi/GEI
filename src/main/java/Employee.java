import java.sql.Date;

public class Employee {
    private int id;
    private String name;
    private String contactNumber;
    private String gender;
    private String dateOfBirth;

    // Constructor
    public Employee(int id, String name, String contactNumber, String gender, String dateOfBirth) {
        this.id = id;
        this.name = name;
        this.contactNumber = contactNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    // Getters and setters
}
