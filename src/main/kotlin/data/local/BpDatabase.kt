package data.local

import com.db.BpDatabase.BpDatabase
import com.db.BpDatabaseQueries
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

abstract class BpDatabase {

    companion object {
        fun getDatabaseDao(): BpDatabaseQueries {
            // Creating a SQLite driver
            val driver = JdbcSqliteDriver("jdbc:sqlite:com.sqldelight.BpDatabase")
            // Executing SQL statement to create the Bp table if it doesn't exist
            driver.execute(
                null, "CREATE TABLE IF NOT EXISTS bp (\n" +
                        " id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        " sbp INTEGER,\n" +
                        " dbp INTEGER,\n" +
                        " event_time TEXT NOT NULL\n" +
                        ");", 0
            )
            // Create an instance of the database
            val database = BpDatabase(driver)
            // Getting the BpDatabaseQueries object associated with the database
            val myDatabaseQueries = database.bpDatabaseQueries
            // Returning the BpDatabaseQueries object
            return myDatabaseQueries
        }
    }
}