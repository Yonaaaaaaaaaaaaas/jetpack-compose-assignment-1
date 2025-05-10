package com.example.library

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.library.ui.theme.LIbraryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LIbraryTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen(onSignOut = {
                        Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show()
                    })
                }
            }
        }
    }
}

@Composable
fun MainScreen(onSignOut: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFF6FF)) // light blue background
    ) {
        Header(onSignOut)
        CourseList(courses = sampleCourses)
    }
}

@Composable
fun Header(onSignOut: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.aaulogo),
                contentDescription = "University Logo",
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 12.dp)
            )
            Text(
                text = "My University Courses",
                style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
            )
        }
        TextButton(onClick = onSignOut) {
            Text("Sign Out", color = Color.White)
        }
    }
}

@Composable
fun CourseList(courses: List<Course>) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(courses) { course ->
            CourseCard(course = course)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun CourseCard(course: Course) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded }
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(course.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Code: ${course.code}", style = MaterialTheme.typography.bodyMedium)
            Text("Credit Hours: ${course.creditHours}", style = MaterialTheme.typography.bodyMedium)

            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Description: ${course.description}", style = MaterialTheme.typography.bodySmall)
                Text("Prerequisites: ${course.prerequisites}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

data class Course(
    val title: String,
    val code: String,
    val creditHours: Int,
    val description: String,
    val prerequisites: String
)

val sampleCourses = listOf(
    Course("Introduction to Programming", "CS101", 3, "Learn programming basics using Kotlin.", "None"),
    Course("Data Structures", "CS202", 4, "Study of lists, stacks, queues, trees, graphs.", "CS101"),
    Course("Operating Systems", "CS303", 4, "Learn how OS manages resources and processes.", "CS202"),
    Course("Mobile Development", "CS404", 3, "Develop Android apps using Kotlin and Jetpack.", "CS202")
)
