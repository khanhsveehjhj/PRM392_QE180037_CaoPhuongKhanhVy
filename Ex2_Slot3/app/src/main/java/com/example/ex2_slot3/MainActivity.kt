package com.example.ex2_slot3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ex2_slot3.ui.theme.Ex2_Slot3Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { Ex2_Slot3Theme { NoteBoardScreen() } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteBoardScreen() {
    var noteText by remember { mutableStateOf("") }
    val notes = remember { mutableStateListOf<String>() }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var draggedNote by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Note Board") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4CAF50),
                    titleContentColor = Color.White
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            // Trash Bin
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Trash Bin",
                    tint = if (draggedNote != null) Color.Red else Color.Gray,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row {
                BasicTextField(
                    value = noteText,
                    onValueChange = { noteText = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .background(Color(0xFFF0F0F0), RoundedCornerShape(8.dp))
                        .padding(12.dp),
                    textStyle = TextStyle(color = Color.Black)
                )

                Button(
                    onClick = {
                        if (noteText.isNotBlank()) {
                            notes.add(noteText)
                            noteText = ""
                        }
                    },
                    enabled = noteText.isNotBlank()
                ) {
                    Text("+ Add")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ScrollView cho danh sách note
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                notes.forEach { note ->
                    var selected by remember { mutableStateOf(false) }

                    Text(
                        text = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(
                                if (selected) Color(0xFFB2DFDB) else Color(0xFFFFF59D),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(12.dp)
                            // Long press để chọn
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onLongPress = {
                                        selected = !selected
                                    }
                                )
                            }
                            // Drag để kéo xuống Trash Bin
                            .pointerInput(note) {
                                detectDragGestures(
                                    onDragStart = { offset ->
                                        draggedNote = note
                                    },
                                    onDragEnd = {
                                        if (draggedNote != null) {
                                            notes.remove(draggedNote)
                                            val deleted = draggedNote
                                            draggedNote = null
                                            // Snackbar + Undo
                                            scope.launch {
                                                val result = snackbarHostState.showSnackbar(
                                                    "Note deleted. Undo?",
                                                    actionLabel = "Undo"
                                                )
                                                if (result == SnackbarResult.ActionPerformed && deleted != null) {
                                                    notes.add(deleted)
                                                }
                                            }
                                        }
                                    },
                                    onDragCancel = {
                                        draggedNote = null
                                    },
                                    onDrag = { change, _ -> // 👈 thêm change để đúng API
                                        change.consume()
                                    }
                                )
                            }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteBoardPreview() {
    Ex2_Slot3Theme { NoteBoardScreen() }
}