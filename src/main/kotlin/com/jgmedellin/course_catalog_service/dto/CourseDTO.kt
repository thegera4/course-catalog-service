package com.jgmedellin.course_catalog_service.dto

import jakarta.validation.constraints.NotBlank

data class CourseDTO (
    val id: Int?,
    @get:NotBlank(message = "Name cannot be blank")
    val name: String,
    @get:NotBlank(message = "Category cannot be blank")
    val category: String,
)