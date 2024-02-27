import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Timestamp
import java.util.*

data class DTR(
    val attendanceId: Int,
    val employeeId: Int,
    val timeIn: Timestamp?,
    val breakOut: Timestamp?,
    val breakIn: Timestamp?,
    val timeOut: Timestamp?,
    val numHours: Double?
)

object DTRProcessor {

    fun recordTimeEntry(connection: Connection, adminId: Int, employeeId: Int): DTR? {
        val latestAttendanceId = AttendanceProcessor.getLatestAttendanceId(connection, adminId)

        if (latestAttendanceId == -1) {
            println("Error: No valid Attendance_ID found for Admin_ID $adminId")
            return null
        }

        val currentDTR = getLatestDTR(connection, latestAttendanceId, employeeId)

        val currentTime = Timestamp(System.currentTimeMillis())

        when {
            currentDTR == null -> {
                // Create a new DTR entry
                val newDTR = DTR(latestAttendanceId, employeeId, currentTime, null, null, null, null)
                insertDTR(connection, newDTR)
                return newDTR
            }
            currentDTR.timeIn == null -> {
                println("Error: Time_In already recorded for Employee_ID $employeeId and Attendance_ID $latestAttendanceId.")
                return null
            }
            currentDTR.breakOut == null -> {
                // Update existing DTR with Break_Out
                updateDTR(connection, currentDTR.copy(breakOut = currentTime))
                return currentDTR.copy(breakOut = currentTime)
            }
            currentDTR.breakIn == null -> {
                // Update existing DTR with Break_In
                updateDTR(connection, currentDTR.copy(breakIn = currentTime))
                return currentDTR.copy(breakIn = currentTime)
            }
            currentDTR.timeOut == null -> {
                // Update existing DTR with Time_Out
                val updatedDTR = currentDTR.copy(timeOut = currentTime)
                updateDTR(connection, updatedDTR.copy(numHours = calculateNumHours(updatedDTR)))
                return updatedDTR
            }
            else -> {
                // Employee completed all entries, create a new DTR entry
                val newDTR = DTR(latestAttendanceId, employeeId, currentTime, null, null, null, null)
                insertDTR(connection, newDTR)
                return newDTR
            }
        }
    }

    private fun getLatestDTR(connection: Connection, attendanceId: Int, employeeId: Int): DTR? {
        val query = "SELECT * FROM DTR WHERE Attendance_ID = ? AND Employee_ID = ? ORDER BY Time_In DESC LIMIT 1"

        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            preparedStatement.setInt(1, attendanceId)
            preparedStatement.setInt(2, employeeId)
            val resultSet = preparedStatement.executeQuery()

            if (resultSet.next()) {
                return DTR(
                    resultSet.getInt("Attendance_ID"),
                    resultSet.getInt("Employee_ID"),
                    resultSet.getTimestamp("Time_In"),
                    resultSet.getTimestamp("Break_Out"),
                    resultSet.getTimestamp("Break_In"),
                    resultSet.getTimestamp("Time_Out"),
                    resultSet.getDouble("NumHours")
                )
            }
        } catch (e: SQLException) {
            println("Error retrieving latest DTR entry: ${e.message}")
        }

        return null
    }

    private fun insertDTR(connection: Connection, dtr: DTR) {
        val query =
            "INSERT INTO DTR (Attendance_ID, Employee_ID, Time_In, Break_Out, Break_In, Time_Out, NumHours) VALUES (?, ?, ?, ?, ?, ?, ?)"

        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            preparedStatement.setInt(1, dtr.attendanceId)
            preparedStatement.setInt(2, dtr.employeeId)
            preparedStatement.setTimestamp(3, dtr.timeIn)
            preparedStatement.setTimestamp(4, dtr.breakOut)
            preparedStatement.setTimestamp(5, dtr.breakIn)
            preparedStatement.setTimestamp(6, dtr.timeOut)
            preparedStatement.setDouble(7, dtr.numHours ?: 0.0)
            preparedStatement.executeUpdate()
        } catch (e: SQLException) {
            // Handle the duplicate entry error or any other SQL-related errors
            println("Error inserting DTR entry: ${e.message}")
        }
    }


    private fun updateDTR(connection: Connection, dtr: DTR) {
        val query =
            "UPDATE DTR SET Time_In = ?, Break_Out = ?, Break_In = ?, Time_Out = ?, NumHours = ? WHERE Attendance_ID = ? AND Employee_ID = ?"

        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            preparedStatement.setTimestamp(1, dtr.timeIn)
            preparedStatement.setTimestamp(2, dtr.breakOut)
            preparedStatement.setTimestamp(3, dtr.breakIn)
            preparedStatement.setTimestamp(4, dtr.timeOut)
            preparedStatement.setDouble(5, dtr.numHours ?: 0.0)
            preparedStatement.setInt(6, dtr.attendanceId)
            preparedStatement.setInt(7, dtr.employeeId)
            preparedStatement.executeUpdate()
        } catch (e: SQLException) {
            println("Error updating DTR entry: ${e.message}")
        }
    }

    private fun calculateNumHours(dtr: DTR): Double {
        // Implement your logic to calculate the number of hours based on Time_In, Break_Out, Break_In, Time_Out
        // For simplicity, assuming a fixed calculation for demonstration purposes
        return 8.0
    }
}
