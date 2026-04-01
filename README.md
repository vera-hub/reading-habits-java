This project is a Java console application that uses SQLite to store and analyze reading habits of users.
The program can import reading data from a semicolon-separated CSV file and provides several functionalities through a menu in the terminal.

Project Contents
This repository contains:
  *src/main/java/org/example/
  *Main.java
  *UserDAO.java
  *ReadingHabitDAO.java
  *DataImporter.java
  *DatabaseSetup.java
  *myfile.db — the SQLite database
  *readinghabits.csv — the CSV file used for importing data
  *README.md — this file

How to Run the Program
Requirements:
  *Java 17 or higher
  *IntelliJ IDEA (recommended)
  *SQLite JDBC driver (already included in the project)

steps:
1. Download or clone this repository
2.Open the project in IntelliJ
3.Make sure myfile.db is in the project root
4.Open Main.java
5.Run the program
6.Use the menu in the terminal to interact with the database


menu functions:
  *add a user
  *show all readinghabits of a user
  *change the title of a book
  *delete a reading habit
  *show the mean age of all users
  *count users who read a specific book
  *show users who read more then one book
  


