import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Employee {

    public static void insertEmployee(Connection connection, String employeeName, String contactNumber, String gender, String dateOfBirth, String jobTypeDescription) {
        try {
            String insertEmployeeQuery = "INSERT INTO Employees (Employee_Name, Contact_Number, Gender, Date_of_Birth) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertEmployeeQuery, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, employeeName);
                preparedStatement.setString(2, contactNumber);
                preparedStatement.setString(3, gender);
                preparedStatement.setString(4, dateOfBirth);
                preparedStatement.executeUpdate();

                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int lastEmployeeId = generatedKeys.getInt(1);
                    insertJobType(connection, lastEmployeeId, jobTypeDescription);
                    System.out.println("Employee successfully inserted.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertJobType(Connection connection, int employeeId, String jobTypeDescription) throws SQLException {
        String jobTypeTable;
        switch (jobTypeDescription) {
            case "regular":
                jobTypeTable = "Regular";
                break;
            case "piecework":
                jobTypeTable = "Piecework";
                break;
            case "admin":
                jobTypeTable = "Admin";
                break;
            default:
                throw new IllegalArgumentException("Invalid Job Type: " + jobTypeDescription);
        }

        // Assuming your JobType table has columns Employee_ID and Job_Type_Description
        String insertJobTypeQuery = "INSERT INTO " + jobTypeTable + " (Employee_ID, Job_Type_Description) VALUES (?, ?)";
        try (PreparedStatement jobTypePreparedStatement = connection.prepareStatement(insertJobTypeQuery)) {
            jobTypePreparedStatement.setInt(1, employeeId);
            jobTypePreparedStatement.setString(2, jobTypeDescription);
            jobTypePreparedStatement.executeUpdate();
        }
    }
}
