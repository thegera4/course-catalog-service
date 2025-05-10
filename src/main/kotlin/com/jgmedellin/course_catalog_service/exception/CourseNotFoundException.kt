package com.jgmedellin.course_catalog_service.exception

import java.lang.RuntimeException

class CourseNotFoundException(message: String): RuntimeException(message)