package com.jgmedellin.controller

import com.jgmedellin.course_catalog_service.CourseCatalogServiceApplication
import com.jgmedellin.course_catalog_service.dto.CourseDTO
import com.jgmedellin.course_catalog_service.entity.Course
import com.jgmedellin.course_catalog_service.repository.CourseRepository
import com.jgmedellin.course_catalog_service.repository.InstructorRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import com.jgmedellin.util.courseEntityList
import com.jgmedellin.util.instructorEntityList
import org.springframework.web.util.UriComponentsBuilder
import kotlin.collections.forEach
import kotlin.test.assertEquals

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [CourseCatalogServiceApplication::class]
)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseController {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var instructorRepository: InstructorRepository

    @BeforeEach
    fun setup() {
        courseRepository.deleteAll()
        instructorRepository.deleteAll()

        val instructors = instructorEntityList()
        instructorRepository.saveAll(instructors)

        val courses = courseEntityList(instructors)
        courseRepository.saveAll(courses)
    }

    @Test
    fun addCourses() {
        val instructor = instructorRepository.findAll().first()

        val courseDTOs = listOf(
            CourseDTO(null, "Building a REST API with Kotlin and SpringBoot",
                "Development", instructor.id)
        )

        val savedCourseDTOs = webTestClient.post()
            .uri("/api/v1/courses")
            .bodyValue(courseDTOs)
            .exchange()
            .expectStatus().isCreated
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        // Check that we got the right number of items back
        Assertions.assertEquals(courseDTOs.size, savedCourseDTOs?.size)
        // Check that the IDs are not null for each item
        savedCourseDTOs?.forEach { courseDTO -> Assertions.assertNotNull(courseDTO.id) }
        // Verify other properties were saved correctly
        Assertions.assertEquals("Building a REST API with Kotlin and SpringBoot", savedCourseDTOs?.get(0)?.name)
        Assertions.assertEquals("Development", savedCourseDTOs?.get(0)?.category)
    }

    @Test
    fun retrieveAllCourses() {
        val courseDTOs = webTestClient.get()
            .uri("/api/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        // Check that we got the right number of items back
        Assertions.assertEquals(3, courseDTOs?.size)
    }

    @Test
    fun retrieveAllCoursesByName() {

        val uri = UriComponentsBuilder.fromUriString("/api/v1/courses")
            .queryParam("course_name", "Programming")
            .toUriString()

        val courseDTOs = webTestClient.get()
            .uri(uri)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        // Check that we got the right number of items back
        Assertions.assertEquals(2, courseDTOs?.size)
    }

    @Test
    fun updateCourse() {
        val instructor = instructorRepository.findAll().first()

        val course = Course(null, "Kotlin Programming", "Development", instructor)
        courseRepository.save(course)

        val updatedCourseDTO = CourseDTO(null, "Advanced Kotlin Programming",
            "Development", course.instructor!!.id)

        val updatedCourse = webTestClient.put()
            .uri("/api/v1/courses/{id}", course.id)
            .bodyValue(updatedCourseDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        // Check that the returned course has the updated values
        assertEquals("Advanced Kotlin Programming", updatedCourse?.name)
    }

    @Test
    fun deleteCourse() {
        val instructor = instructorRepository.findAll().first()

        val course = Course(null, "Kotlin Programming", "Development", instructor)
        courseRepository.save(course)

        webTestClient.delete()
            .uri("/api/v1/courses/{id}", course.id)
            .exchange()
            .expectStatus().isNoContent
    }
}