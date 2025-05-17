package com.kotlinplayground;

import com.jgmedellin.course_catalog_service.entity.Course;
import com.jgmedellin.course_catalog_service.entity.Instructor;
import com.jgmedellin.course_catalog_service.javaTest.InvokeJavaFromKotlinKt;
import com.jgmedellin.course_catalog_service.javaTest.TestDataClass;
import com.jgmedellin.course_catalog_service.javaTest.TestFromKotlin;

import java.util.ArrayList;

public class InvokeKotlinFromJava {

    public static void main(String[] args) {
      // Create instances of Kotlin classes
      var instructor = new Instructor(1, "Gerardo Medellin", new ArrayList<>());
      var course = new Course(1, "Kotlin-Java Interoperability", "Development", instructor);

      System.out.println(course);

      // Call Kotlin functions
      // InvokeJavaFromKotlinKt.printName();
      // InvokeJavaFromKotlinKt.printName("Gerardo Medellin");
      InvokeJavaFromKotlinKt.logName();

      // calling functions inside a companion object in kotlin class
      TestFromKotlin.Companion.logSomething("something");
      TestFromKotlin.logSomething("static method");

      // accessing field from kotlin class
      InvokeJavaFromKotlinKt.exField = 11;
      System.out.println(InvokeJavaFromKotlinKt.exField);

      // accessing object from kotlin class
      //TestFromKotlin.Authenticate.INSTANCE.login("gera");
      TestFromKotlin.Authenticate.login("gera");

      // creating instances of kotlin data class with default values
      var test = new TestDataClass("Gerardo");
      System.out.println(test);
      var test2 = new TestDataClass("Gerardo", 38);
      System.out.println(test2);
    }

}
