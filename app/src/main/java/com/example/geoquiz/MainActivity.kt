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


data class Question (val question: String, val answer: Boolean, var isAnswered: Boolean)

val questions = listOf<Question>(
    Question("Canberra is the capital of Australia.", true, false),
    Question("The Pacific Ocean is larger than the Atlantic Ocean.", true, false),
    Question("The Suez Canal connects the Red Sea and the Indian Ocean.", false, false),
    Question("The source of the Nile River is in Egypt.", false, false),
    Question("The Amazon River is the longest river in the Americas.", true, false),
    Question("Lake Baikal is the world's oldest and deepest freshwater lake.", true, false)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeoQuiz() {
    var currentQuestion : Question by remember {mutableStateOf(questions[0])}
    var questionText by remember {mutableStateOf(questions[0].question)}
    var isAnswersButtonHidden : Boolean by remember {mutableStateOf(false)}

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

                            },
                            modifier = Modifier.width(120.dp)
                        ) { Text("TRUE") }
                        Button(
                            onClick = {

                            },
                            modifier = Modifier.width(120.dp)
                        ) { Text("FALSE") }
                    }
                } else {
                    Spacer(modifier = Modifier.height(48.dp))
                }

                if (currentQuestion != questions[questions.size - 1]) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                val curQuestion = getNextQuestion(currentQuestion)
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
            }
        }
    }
}

fun getNextQuestion(currentQuestion: Question) : Question? {
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