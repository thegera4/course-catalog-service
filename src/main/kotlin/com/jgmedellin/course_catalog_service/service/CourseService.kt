package com.jgmedellin.course_catalog_service.service

import com.jgmedellin.course_catalog_service.dto.CourseDTO
import com.jgmedellin.course_catalog_service.entity.Course
import com.jgmedellin.course_catalog_service.exception.CourseNotFoundException
import com.jgmedellin.course_catalog_service.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CourseService(val courseRepository: CourseRepository) {

    companion object : KLogging()

    /**
     * This method is used to add new course(s) to the database.
     * @param courseDTOs The course data transfer object list containing one or more courses details.
     * @return The saved course data transfer object list.
     */
    fun addCourses(courseDTOs: List<CourseDTO>): List<CourseDTO> {
        val courseEntities = courseDTOs.map { Course(null,it.name,it.category) }
        val savedEntities = courseRepository.saveAll(courseEntities)
        logger.info { "Successfully saved ${savedEntities.count()} new courses." }
        return savedEntities.map { CourseDTO(it.id, it.name, it.category) }
    }

    /**
     * This method retrieves all courses from the database if no course name is provided.
     * If a course name param is provided, it retrieves courses that match the name.
     * @param courseName The name of the course to search for (optional).
     * @return A list of course data transfer objects.
     */
    fun retrieveAllCourses(courseName: String?): List<CourseDTO> {
        val courses = courseName?.let { courseRepository.findCoursesByName(courseName) } ?: courseRepository.findAll()
        return courses.map { CourseDTO(it.id, it.name, it.category) }
    }

    /**
     * This method updates an existing course in the database.
     * @param courseDTO The course data transfer object containing the updated course details.
     * @param courseId The ID of the course to be updated.
     * @return The updated course data transfer object.
     */
    fun updateCourse(courseDTO: CourseDTO, courseId: Int): CourseDTO {
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
    }

    /**
     * This method deletes a course from the database.
     * @param courseId The ID of the course to be deleted.
     */
    fun deleteCourse(courseId: Int) {
        val existingCourse = courseRepository.findById(courseId)

        if (existingCourse.isPresent) {
            existingCourse.get().let { courseRepository.deleteById(courseId) }
        } else {
            throw CourseNotFoundException("Course with ID $courseId not found")
        }
    }

}