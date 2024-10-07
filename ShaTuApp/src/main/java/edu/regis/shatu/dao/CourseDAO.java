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

import edu.regis.shatu.err.NonRecoverableException;
import edu.regis.shatu.err.ObjNotFoundException;
import edu.regis.shatu.model.Course;
import edu.regis.shatu.model.Unit;
import edu.regis.shatu.model.KnowledgeComponent;
import edu.regis.shatu.svc.CourseSvc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * An XML-based Data Access Object implementing CourseSvc behaviors.
 *
 * @author rickb
 */
public class CourseDAO implements CourseSvc {
    // CHANGE INFORMATION FOR YOUR DATABASE.
    private static final String URL = "jdbc:mysql://localhost:3306/student_db"; // database name based on commands in setup.sql
    private static final String USER = "ABCD";          // Your DB details
    private static final String PASSWORD = "###";  // Your DB details

    /**
     * Establish a connection to the MySQL database.
     * 
     * @return Connection to the database
     * @throws SQLException if a connection cannot be established
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Retrieve a Course by its id from the database.
     * 
     * @param id The unique identifier of the Course
     * @return Course object populated with data from the database
     * @throws ObjNotFoundException if no course with the given id is found
     * @throws NonRecoverableException if a database error occurs
     */
    @Override
    public Course retrieve(int id) throws ObjNotFoundException, NonRecoverableException {
        String sql = "SELECT * FROM courses WHERE id = ?";  // SQL query to retrieve the course by id
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set course id in the query
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Create a Course object and populate it with retrieved data
                Course course = new Course(id);
                course.setTitle(rs.getString("title"));
                course.setDescription(rs.getString("description"));
                course.setUnits(retrieveUnitsForCourse(id));
                course.setOutcomes(retrieveOutcomesForCourse(id));
                return course;
            } else {
                throw new ObjNotFoundException("Course not found");
            }
        } catch (SQLException e) {
            throw new NonRecoverableException("Error retrieving course", e);
        }
    }
    /**
     * Retrieve the units associated with a specific course from database.
     * 
     * @param courseId The unique identifier of the Course
     * @return A list of Unit objects associated with the Course
     * @throws NonRecoverableException if a database error occurs
     */
    private ArrayList<Unit> retrieveUnitsForCourse(int courseId) throws NonRecoverableException {
        String sql = "SELECT * FROM units WHERE course_id = ?";
        ArrayList<Unit> units = new ArrayList<>();
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Unit unit = new Unit(rs.getInt("id"));
                unit.setTitle(rs.getString("title"));
                unit.setDescription(rs.getString("description"));
                unit.setSequenceId(rs.getInt("sequence_id"));
                units.add(unit);
            }
        } catch (SQLException e) {
            throw new NonRecoverableException("Error retrieving units", e);
        }
        return units;
    }

    /**
     * Retrieve knowledge component outcomes for a course.
     * 
     * @param courseId The unique identifier of the Course
     * @return A list of KnowledgeComponent objects representing outcomes for the Course
     * @throws NonRecoverableException if a database error occurs
     */
    private ArrayList<KnowledgeComponent> retrieveOutcomesForCourse(int courseId) throws NonRecoverableException {
        String sql = "SELECT * FROM knowledge_components WHERE course_id = ?";
        ArrayList<KnowledgeComponent> outcomes = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                KnowledgeComponent outcome = new KnowledgeComponent(rs.getInt("id"));
                outcome.setTitle(rs.getString("title"));
                outcomes.add(outcome);
            }
        } catch (SQLException e) {
            throw new NonRecoverableException("Error retrieving outcomes", e);
        }
        return outcomes;
    }
}