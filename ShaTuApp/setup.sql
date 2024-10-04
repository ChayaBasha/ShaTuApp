<<<<<<< Updated upstream
-- login to database with root.
CREATE DATABASE student_db;

GRANT ALL PRIVILEGES ON student_db.* TO 'your_username'@'localhost'; -- EDIT Accordingly

USE student_db;

CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255));

CREATE TABLE courses (
    id INT PRIMARY KEY AUTO_INCREMENT,   -- Unique ID
    title VARCHAR(255) NOT NULL,         -- title
    description TEXT);                   -- description

CREATE TABLE units (
    id INT PRIMARY KEY AUTO_INCREMENT,              -- Unique ID
    course_id INT,                                  -- ID of associated course
    sequence_id INT,                                -- Sequence of unit in course
    title VARCHAR(255) NOT NULL,                    -- title
    description TEXT,                               -- description
    FOREIGN KEY (course_id) REFERENCES courses(id));-- Foreign key linking to courses

CREATE TABLE knowledge_components (
    id INT PRIMARY KEY AUTO_INCREMENT,              -- Unique ID
    course_id INT,                                  -- ID of associated course
    title VARCHAR(255) NOT NULL,                    -- title
    bloom_level VARCHAR(255),                       -- Bloom
    is_domain_focus BOOLEAN,                        -- Whether component is domain-focused
    FOREIGN KEY (course_id) REFERENCES courses(id));-- Foreign key linking to courses

-- Populate tables for testing.
INSERT INTO courses (title, description) 
VALUES ('Intro to SHA-256', 'Learn SHA-256 hashing basics.');

INSERT INTO units (course_id, sequence_id, title, description) 
VALUES (1, 1, 'Unit 1: Basics', 'Intro to hashing concepts.');

INSERT INTO knowledge_components (course_id, title, bloom_level, is_domain_focus) 
VALUES (1, 'Understanding Hashing', 'Knowledge', TRUE);
=======
-- SQL COMMANDS USED TO SETUP DATABASE:
 
CREATE DATABASE IF NOT EXISTS student_db;

USE student_db;

CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);
*/
/* -- Uses student_db currently --
CREATE DATABASE IF NOT EXISTS student_db;

USE student_db;

CREATE TABLE courses (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT);

CREATE TABLE units (
    id INT PRIMARY KEY AUTO_INCREMENT,                -- Unique unit ID
    course_id INT,                                    -- ID of course
    sequence_id INT,                                  -- Sequence of the unit in course
    title VARCHAR(255) NOT NULL,                      -- title
    description TEXT,                                 -- Description
    FOREIGN KEY (course_id) REFERENCES courses(id));  -- Foreign key -> to courses

CREATE TABLE knowledge_components (
    id INT PRIMARY KEY AUTO_INCREMENT,                -- Unique ID
    course_id INT,                                    -- ID of course
    title VARCHAR(255) NOT NULL,                      -- title
    bloom_level VARCHAR(255),                         -- Bloom level
    is_domain_focus BOOLEAN,                          -- Whether component is domain-focused
    FOREIGN KEY (course_id) REFERENCES courses(id));  -- Foreign key -> courses

-- I POPULATED THE TABLES WITH THE FOLLOWING, FOR TESTING:
INSERT INTO courses (title, description) VALUES ('Intro to SHA-256', 'Learn the basics of SHA-256 algo.');
INSERT INTO units (course_id, sequence_id, title, description) VALUES (1, 1, 'Unit 1: Basics', 'Introduction to basic hashing.');
INSERT INTO knowledge_components (course_id, title, bloom_level, is_domain_focus) VALUES (1, 'Understanding Hashing', 'Knowledge', TRUE);
*/
>>>>>>> Stashed changes
