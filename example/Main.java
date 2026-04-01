package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:myfile.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println("Connected to database.");

            // Create tables if not exist
            DatabaseSetup.createTables(conn);

            // Import CSV once (comment out after first run)
            DataImporter.importCSV(conn, "readinghabits.csv");

            UserDAO userDAO = new UserDAO(conn);
            ReadingHabitDAO rhDAO = new ReadingHabitDAO(conn);

            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n--- MENU ---");
                System.out.println("1. Add user");
                System.out.println("2. Show reading habits");
                System.out.println("3. change booktitle");
                System.out.println("4. Delete reading habit");
                System.out.println("5. Mean age");
                System.out.println("6. Users who read a book");
                System.out.println("7. Total pages read");
                System.out.println("8. Users who read >1 book");
                System.out.println("9. Exit");

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1 -> {
                        System.out.print("Age: ");
                        int age = Integer.parseInt(sc.nextLine());
                        System.out.print("Name: ");
                        String name = sc.nextLine();
                        userDAO.addUser(age, name);
                    }
                    case 2 -> {
                        System.out.print("UserID: ");
                        int id = Integer.parseInt(sc.nextLine());
                        rhDAO.printReadingHabitsForUser(id);
                    }
                    case 3 -> {
                        System.out.print("Old book title: ");
                        String oldTitle = sc.nextLine();
                        System.out.print("New book title: ");
                        String newTitle = sc.nextLine();
                        rhDAO.updateBookTitle(oldTitle, newTitle);
                    }

                    case 4 -> {
                        System.out.print("UserID: ");
                        int id = Integer.parseInt(sc.nextLine());
                        System.out.print("Book title: ");
                        String title = sc.nextLine();
                        rhDAO.deleteReadingHabit(id, title);
                    }
                    case 5 -> userDAO.printMeanAge();
                    case 6 -> {
                        System.out.print("Book title: ");
                        String title = sc.nextLine();
                        rhDAO.printUsersCountForBook(title);
                    }
                    case 7 -> rhDAO.printTotalPagesRead();
                    case 8 -> rhDAO.printUsersWithMoreThanOneBook();
                    case 9 -> {
                        System.out.println("Goodbye.");
                        return;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
