@file:Suppress("unused")
package com.jgmedellin.course_catalog_service.controller

import com.jgmedellin.course_catalog_service.dto.CourseDTO
import com.jgmedellin.course_catalog_service.service.CourseService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/courses")
@Validated
class CourseController(val courseService: CourseService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody @Valid courseDTOs: List<CourseDTO>): List<CourseDTO> =
        courseService.addCourses(courseDTOs)

    @GetMapping
    fun retrieveAllCourses(@RequestParam("course_name", required = false) name: String?): List<CourseDTO> =
        courseService.retrieveAllCourses(name)

    @PutMapping("/{id}")
    fun updateCourse(@RequestBody courseDTO: CourseDTO, @PathVariable("id") id: Int): CourseDTO =
        courseService.updateCourse(courseDTO, id)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourse(@PathVariable("id") courseId: Int) =
        courseService.deleteCourse(courseId)

}