import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Replace "jdbc:mysql://localhost:3306/im_gei" with your actual database connection URL
        String jdbcUrl = "jdbc:mysql://localhost:3306/im_gei";
        String username = "root";
        String password = "P4ssw0rd";

        try {
            // Establish database connection
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Accept user input for employee registration
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter employee name: ");
            String name = scanner.nextLine();

            System.out.print("Enter contact number: ");
            String contactNumber = scanner.nextLine();

            System.out.print("Enter gender: ");
            String gender = scanner.nextLine();

            System.out.print("Enter date of birth (yyyy-MM-dd): ");
            String dateOfBirth = scanner.nextLine();

            // Register the employee
            registerEmployee(connection, name, contactNumber, gender, dateOfBirth);

            // Close resources
            scanner.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void registerEmployee(Connection connection, String name, String contactNumber, String gender, String dateOfBirth) {
        try {
            // SQL query to insert employee
            String sql = "INSERT INTO Employees (Employee_Name, Contact_Number, Gender, Date_of_Birth) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, contactNumber);
                statement.setString(3, gender);
                statement.setDate(4, Date.valueOf(dateOfBirth));

                // Execute the query
                int rowsAffected = statement.executeUpdate();

                System.out.println("Employee registered. Rows affected: " + rowsAffected);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}