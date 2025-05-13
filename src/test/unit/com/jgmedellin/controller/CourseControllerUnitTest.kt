package com.jgmedellin.controller

import com.jgmedellin.course_catalog_service.CourseCatalogServiceApplication
import com.jgmedellin.course_catalog_service.controller.CourseController
import com.jgmedellin.course_catalog_service.dto.CourseDTO
import com.jgmedellin.course_catalog_service.exceptionhandler.GlobalErrorHandler
import com.jgmedellin.course_catalog_service.service.CourseService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import com.jgmedellin.util.courseDTO
import kotlin.test.assertEquals

@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient
@ContextConfiguration(classes = [CourseCatalogServiceApplication::class, GlobalErrorHandler::class])
class CourseControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseServiceMock: CourseService

    @Test
    fun addCourses() {
        val courseDTOs = listOf(CourseDTO(1, "Kotlin Programming", "Development", 1))

        every { courseServiceMock.addCourses(any()) } returns listOf(courseDTO(courseDTOs[0].id))

        val savedCourseDTOs = webTestClient.post()
            .uri("/api/v1/courses")
            .bodyValue(courseDTOs)
            .exchange()
            .expectStatus().isCreated
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        // Check that we got the right number of items back and that the IDs are not null
        Assertions.assertTrue {
            savedCourseDTOs?.size == courseDTOs.size
            savedCourseDTOs?.get(0)?.id != null
        }
    }

    @Test
    fun addCourses_validation() {
        val courseDTOs = listOf(CourseDTO(null, "", "", 1))

        every { courseServiceMock.addCourses(any()) } returns listOf(courseDTO(id = 1))

        val response = webTestClient.post()
            .uri("/api/v1/courses")
            .bodyValue(courseDTOs)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals("Course name cannot be blank, Course category cannot be blank", response)
    }

    @Test
    fun addCourses_runtimeException() {
        val courseDTOs = listOf(CourseDTO(null, "Kotlin Programming", "Development", 1))

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
}