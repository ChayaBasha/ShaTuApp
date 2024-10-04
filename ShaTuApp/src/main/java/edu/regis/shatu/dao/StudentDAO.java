/*
 * SHATU: SHA-256 Tutor
 * 
 *  (C) Johanna & Richard Blumenthal, All rights reserved
 * 
 *  Unauthorized use, duplication or distribution without the authors'
 *  permission is strictly prohibited.
 * 
 *  Unless required by applicable law or agreed to in writing, this
 *  software is distributed on an "AS IS" basis without warranties
 *  or conditions of any kind, either expressed or implied.
 */
package edu.regis.shatu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import edu.regis.shatu.model.Student;
import edu.regis.shatu.err.IllegalArgException;
import edu.regis.shatu.err.NonRecoverableException;
import edu.regis.shatu.err.ObjNotFoundException;
import edu.regis.shatu.model.aol.StudentModel;
import edu.regis.shatu.svc.StudentSvc;

public class StudentDAO implements StudentSvc {
    // CHANGE INFORMATION FOR YOUR DATABASE.
    private static final String URL = "jdbc:mysql://localhost:3306/student_db"; // database name based on commands in setup.sql
    private static final String USER = "ABCD";          // Your DB details
    private static final String PASSWORD = "###";  // Your DB details

    /**
     * Establishes a connection to MySQL database using URL, username, and password.
     * 
     * @return Connection object to interact with the MySQL database.
     * @throws SQLException if a database access error occurs or the URL is invalid.
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    /**
     * Inserts new student into database.
     * 
     * @param student The Student object containing the user_id, first name, and last name to be added.
     * @throws IllegalArgException if there are invalid arguments.
     * @throws NonRecoverableException if a SQL error occurs during insertion.
     */
    @Override
    public void create(Student student) throws IllegalArgException, NonRecoverableException {
        String sql = "INSERT INTO students (user_id, first_name, last_name) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Set params for the prep'd statement using values from student object.
            stmt.setString(1, student.getUserId());
            stmt.setString(2, student.getFirstName());
            stmt.setString(3, student.getLastName());
            // Execute INSERT statement
            stmt.executeUpdate();
        } catch (SQLException e) {
             // Wrap any SQLException into a NonRecoverableException to ensure consistency.
            throw new NonRecoverableException("Create Student Error", e);
        }
    }
    /**
     * Checks if a student exists in database based on the user_id.
     * 
     * @param userId The user's ID (email) to check for existence in the database.
     * @return true if the student exists, false otherwise.
     * @throws NonRecoverableException if a SQL error occurs during the existence check.
     */
    @Override
    public boolean exists(String userId) throws NonRecoverableException {
        String sql = "SELECT id FROM students WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set user_id parameter in SQL query.
            stmt.setString(1, userId);
            // Execute SELECT query.
            ResultSet rs = stmt.executeQuery();
            // Return true if a result is found -> student exists.
            return rs.next();
        } catch (SQLException e) {
            // Wrap any SQLException into a NonRecoverableException.
            throw new NonRecoverableException("Check Existence Error", e);
        }
    }
    /**
     * Retrieves a student from database based on user_id.
     * 
     * @param userId The user's ID (email) to retrieve the student record.
     * @return A Student object populated with the retrieved student information.
     * @throws ObjNotFoundException if no student is found with the given user_id.
     * @throws NonRecoverableException if a SQL error occurs during the retrieval.
     */
    @Override
    public Student retrieve(String userId) throws ObjNotFoundException, NonRecoverableException {
        String sql = "SELECT * FROM students WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the user_id parameter in the SQL query.
            stmt.setString(1, userId);
            // Execute
            ResultSet rs = stmt.executeQuery();
            // If a record is found, populate and return a Student object.
            if (rs.next()) {
                Student student = new Student();
                student.setUserId(rs.getString("user_id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                return student;
            } else {
                // If no record found, ObjNotFoundException.
                throw new ObjNotFoundException("Student not found");
            }
        } catch (SQLException e) {
            // Wrap any SQLException into a NonRecoverableException.
            throw new NonRecoverableException("Retrieve Student Error", e);
        }
    }
     /**
     * Not implemented yet: Find and return a StudentModel object based on user_id.
     * 
     * @param userId The user's ID (email) to retrieve the StudentModel.
     * @throws ObjNotFoundException if no student model is found.
     * @throws NonRecoverableException if a SQL error occurs during retrieval.
     */
    @Override
    public StudentModel findModelById(String userId) throws ObjNotFoundException, NonRecoverableException {
        // Not implemented yet, but it should work similarly to retrieve(), focusing on StudentModel instead of Student.
        throw new UnsupportedOperationException("Not supported yet.");
    } 
    /**
     * Deletes a student record from database based on user_id.
     * 
     * @param userId The user's ID (email) whose record will be deleted.
     * @throws NonRecoverableException if a SQL error occurs during the deletion or if no record is found.
     */
    @Override
    public void delete(String userId) throws NonRecoverableException {
        String sql = "DELETE FROM students WHERE user_id = ?";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the user_id param in the SQL query.
            stmt.setString(1, userId);
            // Execute DELETE
            int rowsAffected = stmt.executeUpdate();
            // If no rows affected, throw exception -no student was found to delete.
            if (rowsAffected == 0) {
                throw new NonRecoverableException("No student found to delete");
            }
        } catch (SQLException e) {
            throw new NonRecoverableException("Delete Student Error", e);
        }
    }
}