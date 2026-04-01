package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    private final Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public void addUser(int age, String name) {
        String sql = "INSERT INTO User (Age, Name) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, age);
            ps.setString(2, name);
            ps.executeUpdate();
            System.out.println("User added.");
        } catch (Exception e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    public void printMeanAge() {
        String sql = "SELECT AVG(Age) AS mean_age FROM User";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                System.out.println("Mean age: " + rs.getDouble("mean_age"));
            }

        } catch (Exception e) {
            System.out.println("Error calculating mean age: " + e.getMessage());
        }
    }
}
