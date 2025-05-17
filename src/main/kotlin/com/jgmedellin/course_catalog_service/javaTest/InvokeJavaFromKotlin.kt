package com.jgmedellin.course_catalog_service.javaTest

import com.kotlinplayground.StudentJava

@JvmField // This annotation allows you to access the field from Java as a static field
var exField = 10

fun main() {
    val student = StudentJava("John Doe", 20)
    println("Student before change: $student")
    student.name = "Jane Doe" // Change the name and age
    student.age = 21 // with kotlin property access syntax
    println("Student after change: $student")
}

@JvmName("logName") // This annotation allows you to change the name of the function when called from Java
@JvmOverloads // This annotation allows you to call the function from Java with default parameters
fun printName(name: String = "default") {
    println("Name: $name")
}

class TestFromKotlin {

    companion object {
        @JvmStatic // This annotation allows you to call the function from Java as a static method
        fun logSomething(str: String = "default"){
            println("Something: $str")
        }
    }

    object Authenticate {
        @JvmStatic // This annotation allows you to call the function from Java as a static method
        fun login(user: String) {
            println("User $user logged in")
        }
    }

}

data class TestDataClass @JvmOverloads
    constructor (
    val name: String,
    val age: Int? = 18,
)