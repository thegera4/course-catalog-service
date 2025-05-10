package com.jgmedellin.controller

import com.jgmedellin.course_catalog_service.CourseCatalogServiceApplication
import com.jgmedellin.course_catalog_service.controller.GreetingController
import com.jgmedellin.course_catalog_service.service.GreetingService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [GreetingController::class])
@AutoConfigureWebTestClient
@ContextConfiguration(classes = [CourseCatalogServiceApplication::class])
class GreetingControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var greetingServiceMock: GreetingService

    @Test
    fun retrieveGreeting() {
        val name = "John"

        // Mock the service call
        every { greetingServiceMock.retrieveGreeting(any()) } returns "$name, Hello from default profile"

        val result = webTestClient.get()
            .uri("/api/v1/greeting/{name}", name)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()

        Assertions.assertEquals("$name, Hello from default profile", result.responseBody)
    }
}