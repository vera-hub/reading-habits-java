package org.example;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {

    public static void createTables(Connection conn) {
        try (Statement stmt = conn.createStatement()) {

            String createUser = """
                CREATE TABLE IF NOT EXISTS User (
                    UserID INTEGER PRIMARY KEY,
                    Age INTEGER,
                    Name TEXT
                );
                """;

            String createReadingHabit = """
                CREATE TABLE IF NOT EXISTS ReadingHabit (
                    UserID INTEGER,
                    BookTitle TEXT,
                    PagesRead INTEGER,
                    SubmissionMoment TEXT,
                    FOREIGN KEY (UserID) REFERENCES User(UserID)
                );
                """;

            stmt.execute(createUser);
            stmt.execute(createReadingHabit);

            System.out.println("Tables created or already exist.");

        } catch (Exception e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }
}
