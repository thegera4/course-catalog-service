package com.jgmedellin.controller

import com.jgmedellin.course_catalog_service.CourseCatalogServiceApplication
import com.jgmedellin.course_catalog_service.dto.InstructorDTO
import com.jgmedellin.course_catalog_service.repository.InstructorRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import com.jgmedellin.util.*
import org.testcontainers.junit.jupiter.Testcontainers
import kotlin.collections.forEach

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [CourseCatalogServiceApplication::class]
)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@Testcontainers
class InstructorController : PostgreSQLContainerInitializer() {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var instructorRepository: InstructorRepository

    @BeforeEach
    fun setup() {
        instructorRepository.deleteAll()
        val instructors = instructorEntityList()
        instructorRepository.saveAll(instructors)
    }

    @Test
    fun addInstructors() {
        val instructorDTOs = listOf(
            InstructorDTO(null, "John Doe"), InstructorDTO(null, "Jane Smith")
        )

        val savedInstructorDTOs = webTestClient.post()
            .uri("/api/v1/instructors")
            .bodyValue(instructorDTOs)
            .exchange()
            .expectStatus().isCreated
            .expectBodyList(InstructorDTO::class.java)
            .returnResult()
            .responseBody

        // Check that we got the right number of items back
        Assertions.assertEquals(instructorDTOs.size, savedInstructorDTOs?.size)
        // Check that the IDs are not null for each item
        savedInstructorDTOs?.forEach { instructorDTO -> Assertions.assertNotNull(instructorDTO.id) }
        // Verify other properties were saved correctly
        Assertions.assertEquals("John Doe", savedInstructorDTOs?.get(0)?.name)
        Assertions.assertEquals("Jane Smith", savedInstructorDTOs?.get(1)?.name)
    }

    /*
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
        val existingCourse = courseRepository.findAll().first()

        val updatedCourseDTO = CourseDTO(existingCourse.id, "Kotlin Programming", "Development")

        val updatedCourse = webTestClient.put()
            .uri("/api/v1/courses/{id}", existingCourse.id)
            .bodyValue(updatedCourseDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        // Check that the returned course has the updated values
        Assertions.assertEquals("Kotlin Programming", updatedCourse!!.name)
    }

    @Test
    fun deleteCourse() {
        val existingCourse = courseRepository.findAll().first()

        webTestClient.delete()
            .uri("/api/v1/courses/{id}", existingCourse.id)
            .exchange()
            .expectStatus().isNoContent

        // Check that the course was deleted
        val courseDTOs = webTestClient.get()
            .uri("/api/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(2, courseDTOs?.size)
    }
    */
}