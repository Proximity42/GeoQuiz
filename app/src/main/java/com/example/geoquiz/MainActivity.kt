package com.example.geoquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.geoquiz.ui.theme.GeoQuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoQuizTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GeoQuiz(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
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
fun GeoQuiz(modifier: Modifier = Modifier) {
    var questionText by remember {mutableStateOf("")}

    Text(
        text = questionText,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GeoQuizTheme {
        GeoQuiz()
    }
}