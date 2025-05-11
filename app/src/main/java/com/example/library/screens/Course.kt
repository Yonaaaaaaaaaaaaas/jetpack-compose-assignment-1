package com.example.library.screens


data class Course(
    val title: String,
    val code: String,
    val creditHours: Int,
    val description: String,
    val prerequisites: String
)

val sampleCourses = listOf(
    Course("Intro to Programming", "CS101", 3, "Learn Kotlin basics and app development.", "None"),
    Course("Data Structures", "CS202", 4, "Linked lists, stacks, trees, and graphs.", "CS101"),
    Course("Operating Systems", "CS303", 3, "Processes, memory, and file systems.", "CS202"),
    Course("Mobile Development", "CS404", 3, "Android app design using Compose.", "CS202")
)
