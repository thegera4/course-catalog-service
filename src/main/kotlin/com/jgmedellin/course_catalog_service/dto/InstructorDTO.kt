package com.jgmedellin.course_catalog_service.dto

import jakarta.validation.constraints.NotBlank

data class InstructorDTO(
    val id: Int?,
    @get:NotBlank(message = "Instructor name cannot be blank")
    var name: String
)