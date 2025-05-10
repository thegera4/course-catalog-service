package com.jgmedellin.course_catalog_service.repository

import com.jgmedellin.course_catalog_service.entity.Course
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course, Int> {

    // Custom query methods. The method names are derived from the field names in the entities.
    // Spring Data JPA will automatically implement these methods based on the method names.
    // Check the spring docs for more details: https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

    /**
     * Find courses by name containing a specific string (using Spring Data JPA).
     * @param courseName The string to search for in the course name.
     * @return A list of courses that contain the specified string in their name.
     */
    fun findByNameContaining(courseName: String): List<Course>

    /**
     * Find courses by category containing a specific string (with native SQL query).
     * @param courseName The string to search for in the course name.
     * @return A list of courses that contain the specified string in their category.
     */
    @Query(value = "SELECT * FROM courses WHERE name LIKE %?1%", nativeQuery = true)
    fun findCoursesByName(courseName: String): List<Course>

}