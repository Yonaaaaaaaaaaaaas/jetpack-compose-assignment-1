package com.example.library.screens

import com.example.library.R
import kotlin.code


import android.os.Bundle
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.library.ui.theme.LIbraryTheme
import kotlin.system.exitProcess

@Composable
fun MainScreen() {
    var isDarkTheme by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    // Toggle the theme
    LIbraryTheme (darkTheme = isDarkTheme) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Header(onSignOut = { showDialog = true }, onThemeChange = { isDarkTheme = !isDarkTheme })
            CourseList(courses = sampleCourses)
        }

        if (showDialog) {
            SignOutDialog(onDismiss = { showDialog = false }, onConfirm = { exitProcess(0) })
        }
    }
}

@Composable
fun Header(onSignOut: () -> Unit, onThemeChange: (Boolean) -> Unit) {
    Surface(
        tonalElevation = 4.dp,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.aaulogo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "University Courses",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = isSystemInDarkTheme(),
                    onCheckedChange = { onThemeChange(it) },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color.White)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = onSignOut) {
                    Text("Sign Out", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun SignOutDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirm Sign Out") },
        text = { Text("Are you sure you want to sign out?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("No")
            }
        }
    )
}

@Composable
fun CourseList(courses: List<Course>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(courses) { course ->
            CourseCard(course)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun CourseCard(course: Course) {
    var isExpanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (isExpanded) 180f else 0f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = course.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Code: ${course.code}")
                    Text(text = "Credits: ${course.creditHours}")
                }
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = Icons.Filled.ExpandMore,
                        contentDescription = "Expand",
                        modifier = Modifier.rotate(rotation)
                    )
                }
            }
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Description: ${course.description}")
                Text(text = "Prerequisites: ${course.prerequisites}")
            }
        }
    }
}
