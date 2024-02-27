import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException
import DatabaseConnector

object RateUpdater {
    @Throws(SQLException::class)
    fun updateRates(smallRate: Double, mediumRate: Double, largeRate: Double) {
        val query = "UPDATE PackType SET Price = ? WHERE Size = ?"
        val sizes = arrayOf("small", "medium", "large")
        val connection: Connection = DatabaseConnector.getConnection()
        val statement: PreparedStatement = connection.prepareStatement(query)
        for (i in sizes.indices) {
            statement.setDouble(1, when (i) {
                0 -> smallRate
                1 -> mediumRate
                2 -> largeRate
                else -> throw IllegalArgumentException("Invalid size index")
            })
            statement.setString(2, sizes[i])
            statement.addBatch()
        }
        statement.executeBatch()
        statement.close()
        connection.close()
    }
}

fun main() {
    try {
        // Input your rates here
        val smallRate = 5.0
        val mediumRate = 10.0
        val largeRate = 20.0

        RateUpdater.updateRates(smallRate, mediumRate, largeRate)
        println("Rates updated successfully.")
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}


