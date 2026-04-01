package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReadingHabitDAO {
    private final Connection conn;

    public ReadingHabitDAO(Connection conn) {
        this.conn = conn;
    }

    public void printReadingHabitsForUser(int userId) {
        String sql = "SELECT BookTitle, PagesRead, SubmissionMoment FROM ReadingHabit WHERE UserID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println(
                        "Book: " + rs.getString("BookTitle") +
                                ", Pages: " + rs.getInt("PagesRead") +
                                ", Submitted: " + rs.getString("SubmissionMoment")
                );
            }

            if (!found) {
                System.out.println("No reading habits for this user.");
            }

        } catch (Exception e) {
            System.out.println("Error reading habits: " + e.getMessage());
        }
    }

    public void deleteReadingHabit(int userId, String bookTitle) {
        String sql = "DELETE FROM ReadingHabit WHERE UserID = ? AND BookTitle = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, bookTitle);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Reading habit deleted.");
            } else {
                System.out.println("No such record.");
            }

        } catch (Exception e) {
            System.out.println("Error deleting habit: " + e.getMessage());
        }
    }

    public void printUsersCountForBook(String bookTitle) {
        String sql = "SELECT COUNT(DISTINCT UserID) AS cnt FROM ReadingHabit WHERE BookTitle = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bookTitle);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Users who read this book: " + rs.getInt("cnt"));
            }

        } catch (Exception e) {
            System.out.println("Error counting users: " + e.getMessage());
        }
    }

    public void printTotalPagesRead() {
        String sql = "SELECT SUM(PagesRead) AS total FROM ReadingHabit";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("Total pages read: " + rs.getInt("total"));

        } catch (Exception e) {
            System.out.println("Error calculating total pages: " + e.getMessage());
        }
    }

    public void printUsersWithMoreThanOneBook() {
        String sql = """
            SELECT COUNT(*) AS cnt
            FROM (
                SELECT UserID, COUNT(*) AS books
                FROM ReadingHabit
                GROUP BY UserID
                HAVING books > 1
            )
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("Users who read more than one book: " + rs.getInt("cnt"));

        } catch (Exception e) {
            System.out.println("Error counting users: " + e.getMessage());
        }
    }
    public void updateBookTitle(String oldTitle, String newTitle) {
        String sql = "UPDATE ReadingHabit SET BookTitle = ? WHERE BookTitle = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newTitle);
            ps.setString(2, oldTitle);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Book title updated in " + rows + " record(s).");
            } else {
                System.out.println("No records found with that book title.");
            }

        } catch (Exception e) {
            System.out.println("Error updating book title: " + e.getMessage());
        }
    }

}

