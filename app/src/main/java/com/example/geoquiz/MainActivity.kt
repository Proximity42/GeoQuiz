package com.example.geoquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.geoquiz.ui.theme.GeoQuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoQuizTheme {
                GeoQuiz()
            }
        }
    }
}


data class Question (
    val question: String,
    val answer: Boolean,
    var isAnswered: Boolean,
    var userAnswer: Boolean?
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeoQuiz() {
    var questions by remember {mutableStateOf(listOf(
        Question("Canberra is the capital of Australia.", true, false, null),
        Question("The Pacific Ocean is larger than the Atlantic Ocean.", true, false, null),
        Question("The Suez Canal connects the Red Sea and the Indian Ocean.", false, false, null),
        Question("The source of the Nile River is in Egypt.", false, false, null),
        Question("The Amazon River is the longest river in the Americas.", true, false, null),
        Question("Lake Baikal is the world's oldest and deepest freshwater lake.", true, false, null)
    ))}

    var currentQuestion : Question by remember {mutableStateOf(questions[0])}
    var questionText by remember {mutableStateOf(questions[0].question)}

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GeoQuiz", fontSize = 24.sp) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
        {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = questionText,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                )
                if (!currentQuestion.isAnswered) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                val curIndex : Int = questions.indexOf(currentQuestion)
                                val newCurQuestion = currentQuestion.copy()
                                newCurQuestion.isAnswered = true
                                newCurQuestion.userAnswer = true
                                questions = questions.toMutableList().also {
                                    it[curIndex] = newCurQuestion
                                }
                                currentQuestion = newCurQuestion
                            },
                            modifier = Modifier.width(120.dp)
                        ) { Text("TRUE") }
                        Button(
                            onClick = {
                                val curIndex : Int = questions.indexOf(currentQuestion)
                                val newCurQuestion = currentQuestion.copy()
                                newCurQuestion.isAnswered = true
                                newCurQuestion.userAnswer = false
                                questions = questions.toMutableList().also {
                                    it[curIndex] = newCurQuestion
                                }
                                currentQuestion = newCurQuestion
                            },
                            modifier = Modifier.width(120.dp)
                        ) { Text("FALSE") }
                    }
                } else {
                    Spacer(modifier = Modifier.height(25.dp))
                }

                if (currentQuestion.isAnswered) {
                    var result : String
                    if (currentQuestion.answer == currentQuestion.userAnswer) {
                        result = "Correct. Correct answer: " + currentQuestion.answer.toString()
                    } else {
                        result = "Incorrect. Correct answer: " + currentQuestion.answer.toString()
                    }
                    Text(text=result, fontSize=20.sp, modifier = Modifier.padding(vertical = 10.dp))
                } else {
                    Spacer(modifier = Modifier.height(35.dp))
                }

                if (currentQuestion != questions[questions.size - 1]) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                val curQuestion = getNextQuestion(questions, currentQuestion)
                                if (curQuestion != null) {
                                    currentQuestion = curQuestion
                                    questionText = curQuestion.question
                                }
                            },
                            enabled = currentQuestion.isAnswered,
                            modifier = Modifier.width(120.dp)
                        ) { Text("NEXT") }
                    }
                }

                if (currentQuestion == questions[questions.size - 1] && currentQuestion.isAnswered) {
                    Text(text="Statistics", fontSize=26.sp, modifier = Modifier.padding(vertical=5.dp))
                    Text(
                        text="Count correct answers: " + questions
                            .count{it.answer == it.userAnswer}.toString(),
                        fontSize = 20.sp,
                        modifier= Modifier.padding(vertical=5.dp)
                    )
                }
            }
        }
    }
}

fun getNextQuestion(questions: List<Question>, currentQuestion: Question) : Question? {
    val currentIndex = questions.indexOf(currentQuestion)
    if (currentIndex == -1) {
       return null
    }
    return questions.getOrNull(currentIndex + 1)
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GeoQuizTheme {
        GeoQuiz()
    }
}