import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class EmployeeRegistration {

    public static void insertEmployee(Connection connection) throws SQLException {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter Employee Name: ");
            String employeeName = scanner.nextLine();

            System.out.print("Enter Contact Number: ");
            String contactNumber = scanner.nextLine();

            System.out.print("Enter Gender: ");
            String gender = scanner.nextLine();

            System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
            String dateOfBirth = scanner.nextLine();

            System.out.print("Enter Job Type (Regular/Piecework/Admin): ");
            String jobTypeDescription = scanner.nextLine().toLowerCase();

            Employee.insertEmployee(connection, employeeName, contactNumber, gender, dateOfBirth, jobTypeDescription);
        }
    }
}
