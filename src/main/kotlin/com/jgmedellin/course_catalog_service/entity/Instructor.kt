package com.jgmedellin.course_catalog_service.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "Instructors")
data class Instructor(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    var name: String,
    @OneToMany(mappedBy = "instructor", cascade = [CascadeType.ALL], orphanRemoval = true, targetEntity = Course::class)
    var courses: List<Course> = mutableListOf()
)
