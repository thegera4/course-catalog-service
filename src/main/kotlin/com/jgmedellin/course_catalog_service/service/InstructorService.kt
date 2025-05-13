package com.jgmedellin.course_catalog_service.service

import com.jgmedellin.course_catalog_service.dto.InstructorDTO
import com.jgmedellin.course_catalog_service.entity.Instructor
import com.jgmedellin.course_catalog_service.repository.InstructorRepository
import mu.KLogging
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class InstructorService(val instructorRepository: InstructorRepository) {

    companion object : KLogging()

    /**
     * This method is used to add new instructor(s) to the database.
     * @param instructorDTOs The instructor data transfer object list containing one or more instructors details.
     * @return The saved instructor data transfer object list.
     */
    fun addInstructor(instructorDTOs: List<InstructorDTO>): List<InstructorDTO> {
        val instructorEntities = instructorDTOs.map { Instructor(null,it.name) }
        val savedEntities = instructorRepository.saveAll(instructorEntities)
        logger.info { "Successfully saved ${savedEntities.count()} new instructors." }
        return savedEntities.map { InstructorDTO(it.id, it.name) }
    }

    /**
     * This method retrieves an instructor by their ID.
     * @param instructorId The ID of the instructor to be retrieved.
     * @return An Optional containing the instructor if found, or empty if not found.
     */
    fun findByInstructorId(instructorId: Int): Optional<Instructor> {
        return instructorRepository.findById(instructorId)
    }

    /**
     * This method retrieves all courses from the database if no course name is provided.
     * If a course name param is provided, it retrieves courses that match the name.
     * @param courseName The name of the course to search for (optional).
     * @return A list of course data transfer objects.
     */
    /*fun retrieveAllCourses(courseName: String?): List<CourseDTO> {
        val courses = courseName?.let { courseRepository.findCoursesByName(courseName) } ?: courseRepository.findAll()
        return courses.map { CourseDTO(it.id, it.name, it.category) }
    }*/

    /**
     * This method updates an existing course in the database.
     * @param courseDTO The course data transfer object containing the updated course details.
     * @param courseId The ID of the course to be updated.
     * @return The updated course data transfer object.
     */
    /*fun updateCourse(courseDTO: CourseDTO, courseId: Int): CourseDTO {
        val existingCourse = courseRepository.findById(courseId)

        return if (existingCourse.isPresent) {
            existingCourse.get().let {
                it.name = courseDTO.name // update the course properties
                it.category = courseDTO.category // update the course properties
                courseRepository.save(it) // save the updated course
                CourseDTO(it.id, it.name, it.category) // we return the updated course
            }
        } else {
            throw CourseNotFoundException("Course with ID $courseId not found")
        }
    }*/

    /**
     * This method deletes a course from the database.
     * @param courseId The ID of the course to be deleted.
     */
    /*fun deleteCourse(courseId: Int) {
        val existingCourse = courseRepository.findById(courseId)

        if (existingCourse.isPresent) {
            existingCourse.get().let { courseRepository.deleteById(courseId) }
        } else {
            throw CourseNotFoundException("Course with ID $courseId not found")
        }
    }*/

}