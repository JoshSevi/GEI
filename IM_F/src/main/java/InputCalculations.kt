import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

object PieceworkProcessor {
    @Throws(SQLException::class)
    fun processPiecework(employee: Employee, packType: PackType, quantity: Int) {
        val transactionAmount = packType.rate * quantity
        savePieceworkTransaction(employee.employeeId, packType.id, quantity, transactionAmount)
    }

    @Throws(SQLException::class)
    private fun savePieceworkTransaction(employeeID: Int, packTypeID: Int, quantity: Int, transactionAmount: Double) {
        val query = "INSERT INTO Piecework_Details (Employee_ID, PackType_ID, Quantity, Transaction_Amount) VALUES (?, ?, ?, ?)"
        val connection: Connection = DatabaseConnector.getConnection()
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setInt(1, employeeID)
        statement.setInt(2, packTypeID)
        statement.setInt(3, quantity)
        statement.setDouble(4, transactionAmount)
        statement.executeUpdate()
        statement.close()
        connection.close()
    }
}

fun main() {
    try {
        // Example: Employee ID and Pack Type ID obtained from user input
        println("Enter Employee ID:")
        val employeeID = readLine()!!.toInt()

        println("Enter Pack Type ID:")
        val packTypeID = readLine()!!.toInt()

        println("Enter Quantity:")
        val quantity = readLine()!!.toInt()

        val employee = getEmployeeDetails(employeeID)
        val packType = getPackTypeDetails(packTypeID)

        if (employee != null && packType != null) {
            PieceworkProcessor.processPiecework(employee, packType, quantity)
            println("Piecework transaction processed successfully.")
        } else {
            println("Employee or PackType not found.")
        }
    } catch (e: NumberFormatException) {
        println("Invalid input. Please enter a valid number.")
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

@Throws(SQLException::class)
private fun getEmployeeDetails(employeeID: Int): Employee? {
    // Query the database to get employee details
    // Replace this with your actual database query logic
    // Return an instance of Employee if found, or null if not found
    return null
}

@Throws(SQLException::class)
private fun getPackTypeDetails(packTypeID: Int): PackType? {
    // Query the database to get pack type details
    // Replace this with your actual database query logic
    // Return an instance of PackType if found, or null if not found
    return null
}