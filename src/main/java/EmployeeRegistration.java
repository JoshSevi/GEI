import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRegistration {
    private final DataSource dataSource;

    // JDBC URL for connecting to MySQL
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/im_gei";

    public EmployeeRegistration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void registerEmployee(String name, String contactNumber, String gender, String dateOfBirth) {
        try (Connection connection = dataSource.getConnection()) {
            // SQL query to insert employee
            String sql = "INSERT INTO employees (name, contact_number, gender, date_of_birth) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, name);
                statement.setString(2, contactNumber);
                statement.setString(3, gender);
                statement.setDate(4, Date.valueOf(dateOfBirth));

                // Execute the query
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        System.out.println("Employee registered with ID: " + generatedId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            // SQL query to select all employees
            String sql = "SELECT * FROM employees";

            try (Statement statement = connection.createStatement()) {
                // Execute the query
                ResultSet resultSet = statement.executeQuery(sql);

                // Process the result set
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String contactNumber = resultSet.getString("contact_number");
                    String gender = resultSet.getString("gender");
                    String dateOfBirth = resultSet.getString("date_of_birth");

                    Employee employee = new Employee(id, name, contactNumber, gender, dateOfBirth);
                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }
}
