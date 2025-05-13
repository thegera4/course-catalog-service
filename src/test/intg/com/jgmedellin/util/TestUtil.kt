package com.jgmedellin.util

import com.jgmedellin.course_catalog_service.dto.CourseDTO
import com.jgmedellin.course_catalog_service.dto.InstructorDTO
import com.jgmedellin.course_catalog_service.entity.Course
import com.jgmedellin.course_catalog_service.entity.Instructor

fun courseEntityList(instructors: List<Instructor>) = listOf(
    Course(null, "UX/UI Basics", "Design", instructors[1]),
    Course(null, "Python Programming", "Development", instructors[0]),
    Course(null, "JavaScript Programming", "Development", instructors[0]),
)


fun courseDTO(id: Int? =null, name: String ="UX/UI Basics", category: String ="Design") = CourseDTO(id,name,category)

fun instructorEntityList() = listOf(Instructor(null, "John Doe"), Instructor(null, "Jane Smith"))

fun instructorDTO(id: Int? = null, name: String = "John Doe") = InstructorDTO(id, name)