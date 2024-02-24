import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            EmployeeRegistration.insertEmployee(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
