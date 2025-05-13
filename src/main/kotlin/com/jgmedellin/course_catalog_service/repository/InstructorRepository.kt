package com.jgmedellin.course_catalog_service.repository

import com.jgmedellin.course_catalog_service.entity.Instructor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface InstructorRepository : CrudRepository<Instructor, Int> {

    // Custom query methods. The method names are derived from the field names in the entities.
    // Spring Data JPA will automatically implement these methods based on the method names.
    // Check the spring docs for more details: https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

    /**
     * Find instructors by name containing a specific string (using Spring Data JPA).
     * @param instructorName The string to search for in the instructor name.
     * @return A list of instructors that contain the specified string in their name.
     */
    fun findByNameContaining(instructorName: String): List<Instructor>

    /**
     * Find instructor by category containing a specific string (with native SQL query).
     * @param instructorName The string to search for in the instructor name.
     * @return A list of instructors that contain the specified string in their category.
     */
    @Query(value = "SELECT * FROM instructors WHERE name LIKE %?1%", nativeQuery = true)
    fun findInstructorsByName(instructorName: String): List<Instructor>

}