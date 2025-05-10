package com.jgmedellin.util

import com.jgmedellin.course_catalog_service.dto.CourseDTO
import com.jgmedellin.course_catalog_service.entity.Course

fun courseEntityList() = listOf(
    Course(null, "UX/UI Basics", "Design"),
    Course(null, "Python Programming", "Development"),
    Course(null, "JavaScript Programming", "Development")
)

fun courseDTO(id: Int? = null, name: String = "UX/UI Basics", category: String = "Design") = CourseDTO(id, name, category)