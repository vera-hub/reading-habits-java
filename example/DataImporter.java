package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DataImporter {

    public static void importCSV(Connection conn, String filePath) {
        String insertUser = "INSERT OR IGNORE INTO User (UserID, Age, Name) VALUES (?, NULL, NULL)";
        String insertHabit = "INSERT INTO ReadingHabit (UserID, BookTitle, PagesRead, SubmissionMoment) VALUES (?, ?, ?, ?)";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");

                int habitId = Integer.parseInt(parts[0]); // ignored
                int userId = Integer.parseInt(parts[1]);
                int pagesRead = Integer.parseInt(parts[2]);
                String bookTitle = parts[3];
                String submissionMoment = parts[4];

                // Insert user
                try (PreparedStatement ps = conn.prepareStatement(insertUser)) {
                    ps.setInt(1, userId);
                    ps.executeUpdate();
                }

                // Insert reading habit
                try (PreparedStatement ps = conn.prepareStatement(insertHabit)) {
                    ps.setInt(1, userId);
                    ps.setString(2, bookTitle);
                    ps.setInt(3, pagesRead);
                    ps.setString(4, submissionMoment);
                    ps.executeUpdate();
                }
            }

            System.out.println("CSV data imported successfully.");

        } catch (Exception e) {
            System.out.println("Error importing CSV: " + e.getMessage());
        }
    }
}
