package com.jgmedellin.repository

import com.jgmedellin.course_catalog_service.CourseCatalogServiceApplication
import com.jgmedellin.course_catalog_service.exceptionhandler.GlobalErrorHandler
import com.jgmedellin.course_catalog_service.repository.CourseRepository
import com.jgmedellin.course_catalog_service.repository.InstructorRepository
import com.jgmedellin.util.courseEntityList
import com.jgmedellin.util.instructorEntityList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import java.util.stream.Stream

@DataJpaTest
@ActiveProfiles("test")
@ContextConfiguration(classes = [CourseCatalogServiceApplication::class, GlobalErrorHandler::class])
class CourseRepositoryIntgTest {

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var instructorRepository: InstructorRepository

    @BeforeEach
    fun setup() {
        courseRepository.deleteAll()

        val instructors = instructorEntityList()
        instructorRepository.saveAll(instructors)

        val courses = courseEntityList(instructors)
        courseRepository.saveAll(courses)
    }

    @Test
    fun findByNameContaining() {
        val courses = courseRepository.findByNameContaining("Programming")
        println("courses: $courses")
        Assertions.assertEquals(2, courses.size)
    }

    @Test
    fun findCoursesByName() {
        val courses = courseRepository.findCoursesByName("Programming")
        println("courses: $courses")
        Assertions.assertEquals(2, courses.size)
    }

    @ParameterizedTest
    @MethodSource("courseAndSize")
    fun findCoursesByName_approach2(courseName: String, expectedSize: Int) {
        val courses = courseRepository.findCoursesByName(courseName)
        println("courses: $courses")
        Assertions.assertEquals(expectedSize, courses.size)
    }

    companion object {

        @JvmStatic
        fun courseAndSize(): Stream<Arguments> {
            return Stream.of(
                Arguments.arguments("Programming", 2),
            )
        }
    }
}