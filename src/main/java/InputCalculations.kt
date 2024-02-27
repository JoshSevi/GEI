import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

object PieceworkProcessor {
    @Throws(SQLException::class)
    fun processPiecework(employeeID: Int, packTypeID: Int, quantity: Int) {
        val rate = getRateForPackType(packTypeID)
        val transactionAmount = rate * quantity
        savePieceworkTransaction(employeeID, packTypeID, quantity, transactionAmount)
    }

    @Throws(SQLException::class)
    private fun getRateForPackType(packTypeID: Int): Double {
        val query = "SELECT Price FROM packtype WHERE PackTypeID = ?"
        val connection: Connection = DatabaseConnector.getConnection()
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setInt(1, packTypeID)
        val resultSet: ResultSet = statement.executeQuery()
        var rate = 0.0
        if (resultSet.next()) {
            rate = resultSet.getDouble("Price")
        }
        resultSet.close()
        statement.close()
        connection.close()
        return rate
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

        PieceworkProcessor.processPiecework(employeeID, packTypeID, quantity)
        println("Piecework transaction processed successfully.")
    } catch (e: NumberFormatException) {
        println("Invalid input. Please enter a valid number.")
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
