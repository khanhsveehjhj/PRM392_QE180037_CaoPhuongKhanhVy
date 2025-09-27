package com.example.quizactivity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Question(val text: String, val options: List<String>, val correctIndex: Int)

class QuizActivity : ComponentActivity() {

    private val questions = listOf(
        Question("What is the capital of France?", listOf("Berlin", "Madrid", "Paris", "Rome"), 2),
        Question("2 + 2 = ?", listOf("3", "4", "5", "6"), 1),
        Question("Kotlin is developed by?", listOf("JetBrains", "Google", "Apple", "Microsoft"), 0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("QuizActivity", "onCreate")

        val startIndex = savedInstanceState?.getInt("questionIndex") ?: 0
        val startScore = savedInstanceState?.getInt("score") ?: 0

        val playerName = intent.getStringExtra("playerName") ?: "Guest"

        setContent {
            QuizScreen(
                playerName = playerName,
                questions = questions,
                startIndex = startIndex,
                startScore = startScore,
                onStateChange = { idx, sc ->
                }
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart(); Log.d("QuizActivity", "onStart")
    }

    override fun onResume() {
        super.onResume(); Log.d("QuizActivity", "onResume")
    }

    override fun onPause() {
        super.onPause(); Log.d("QuizActivity", "onPause")
    }

    override fun onStop() {
        super.onStop(); Log.d("QuizActivity", "onStop")
    }

    override fun onRestart() {
        super.onRestart(); Log.d("QuizActivity", "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy(); Log.d("QuizActivity", "onDestroy")
    }
}

@Composable
fun QuizScreen(
    playerName: String,
    questions: List<Question>,
    startIndex: Int = 0,
    startScore: Int = 0,
    onStateChange: (Int, Int) -> Unit = { _, _ -> }
) {
    var index by rememberSaveable { mutableIntStateOf(startIndex) }
    var score by rememberSaveable { mutableIntStateOf(startScore) }

    val context = LocalContext.current
    val current = questions[index]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Welcome, $playerName!", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Question ${index + 1}: ${current.text}",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(12.dp))

        current.options.forEachIndexed { i, option ->
            Button(
                onClick = {
                    if (i == current.correctIndex) {
                        score++
                        Toast.makeText(context, "Correct!", Toast.LENGTH_SHORT).show()

                        if (index < questions.lastIndex) {
                            index++
                        }
                    } else {
                        Toast.makeText(context, "Wrong!", Toast.LENGTH_SHORT).show()
                    }
                    onStateChange(index, score)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(option)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Score: $score", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                if (index < questions.lastIndex) index++
                onStateChange(index, score)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next Question")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    val sample = listOf(
        Question(
            "What is the capital of France?",
            listOf("Berlin", "Madrid", "Paris", "Rome"),
            2
        )
    )
    QuizScreen(
        playerName = "Alice",
        questions = sample,
        startIndex = 0,
        startScore = 0,
        onStateChange = { _, _ -> })
}