package com.jgmedellin.course_catalog_service.controller

import com.jgmedellin.course_catalog_service.service.GreetingService
import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/greeting")
class GreetingController(val greetingService: GreetingService) {

    companion object : KLogging() // Get a logger for this class

    @GetMapping("/{name}")
    fun greeting(@PathVariable("name") name: String): String {
        logger.info("Greeting request for name: $name")
        return greetingService.retrieveGreeting(name)
    }
}