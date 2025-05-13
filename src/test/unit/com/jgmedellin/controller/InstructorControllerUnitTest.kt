package com.jgmedellin.controller

import com.jgmedellin.course_catalog_service.CourseCatalogServiceApplication
import com.jgmedellin.course_catalog_service.controller.InstructorController
import com.jgmedellin.course_catalog_service.dto.InstructorDTO
import com.jgmedellin.course_catalog_service.service.InstructorService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import com.jgmedellin.util.instructorDTO
import kotlin.test.assertEquals

@WebMvcTest(controllers = [InstructorController::class])
@AutoConfigureWebTestClient
@ContextConfiguration(classes = [CourseCatalogServiceApplication::class/*, GlobalErrorHandler::class*/])
class InstructorControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var instructorServiceMock: InstructorService

    @Test
    fun addCourses() {
        val instructorDTOs = listOf(InstructorDTO(null, "John Doe"))

        every { instructorServiceMock.addInstructor(any()) } returns listOf(instructorDTO(id = 1))

        val savedInstructorDTOs = webTestClient.post()
            .uri("/api/v1/instructors")
            .bodyValue(instructorDTOs)
            .exchange()
            .expectStatus().isCreated
            .expectBodyList(InstructorDTO::class.java)
            .returnResult()
            .responseBody

        // Check that we got the right number of items back and that the IDs are not null
        Assertions.assertTrue {
            savedInstructorDTOs?.size == instructorDTOs.size
            savedInstructorDTOs?.get(0)?.id != null
        }
    }


    @Test
    fun addCourses_validation() {
        val instructorDTOs = listOf(InstructorDTO(null, ""))

        every { instructorServiceMock.addInstructor(any()) } returns listOf(InstructorDTO(id = 1, name = ""))

        val response = webTestClient.post()
            .uri("/api/v1/instructors")
            .bodyValue(instructorDTOs)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals("Instructor name cannot be blank", response)
    }

    /*
    @Test
    fun addCourses_runtimeException() {
        val courseDTOs = listOf(CourseDTO(null, "Kotlin Programming", "Development"))

        val errorMsg = "Unexpected error occurred"
        every { courseServiceMock.addCourses(any()) } throws RuntimeException(errorMsg)

        val response = webTestClient.post()
            .uri("/api/v1/courses")
            .bodyValue(courseDTOs)
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals(errorMsg, response)
    }

    @Test
    fun retrieveAllCourses() {

        every { courseServiceMock.retrieveAllCourses(any()) }.returnsMany(
            listOf(
                courseDTO(1,"Kotlin Programming","Development"),
                courseDTO(2,"Java Programming","Development"),
            )
        )

        val retrievedCourseDTOs = webTestClient.get()
            .uri("/api/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        // Check that we got the right number of items back
        Assertions.assertEquals(2, retrievedCourseDTOs!!.size)
    }

    @Test
    fun updateCourse() {
        val course = Course(null, "Kotlin Programming", "Development")

        every { courseServiceMock.updateCourse(any(), any()) } returns courseDTO(
            100, "Kotlin Programming 2", "Development")

        val updatedCourseDTO = CourseDTO(100, "Kotlin Programming", "Development")

        val updatedCourse = webTestClient.put()
            .uri("/api/v1/courses/{id}", 100)
            .bodyValue(updatedCourseDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        // Check that we got the right item back
        Assertions.assertEquals("Kotlin Programming 2", updatedCourse!!.name)
    }

    @Test
    fun deleteCourse() {

        every { courseServiceMock.deleteCourse(any()) } just runs

        webTestClient.delete()
            .uri("/api/v1/courses/{id}", 100)
            .exchange()
            .expectStatus().isNoContent
    }
    */
}