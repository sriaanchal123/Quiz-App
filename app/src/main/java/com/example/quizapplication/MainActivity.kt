package com.example.quizapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapplication.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizApplicationTheme {
                MainContent()
            }
        }
    }
}

@Composable
fun MainContent() {
    var currentScreen by remember { mutableStateOf("home") }
    var selectedCategory by remember { mutableStateOf(Category.GENERAL_KNOWLEDGE) }
    var selectedDifficulty by remember { mutableStateOf(Difficulty.EASY) }
    var userName by remember { mutableStateOf("") }
    var finalScore by remember { mutableIntStateOf(0) }
    var totalQuestions by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = SkyBlue
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {
                "home" -> HomeScreen(
                    onStartQuiz = { category, difficulty, name ->
                        selectedCategory = category
                        selectedDifficulty = difficulty
                        userName = name
                        currentScreen = "quiz"
                    }
                )
                "quiz" -> {
                    val filteredQuestions = sampleQuestions.filter {
                        it.category == selectedCategory && it.difficulty == selectedDifficulty
                    }.take(4)
                    QuizScreen(
                        questions = filteredQuestions,
                        onQuizFinished = { score ->
                            finalScore = score
                            totalQuestions = filteredQuestions.size
                            currentScreen = "result"
                        }
                    )
                }
                "result" -> {
                    ResultScreen(
                        score = finalScore,
                        total = totalQuestions,
                        userName = userName,
                        onTryAgain = { currentScreen = "home" }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    onStartQuiz: (Category, Difficulty, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(Category.GENERAL_KNOWLEDGE) }
    var difficulty by remember { mutableStateOf(Difficulty.EASY) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Quiz App",
                    style = MaterialTheme.typography.displaySmall,
                    color = DeepPurple,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Enter Your Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "Select Category", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                
                Category.entries.forEach { cat ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { category = cat }
                    ) {
                        RadioButton(selected = (category == cat), onClick = { category = cat })
                        Text(text = cat.name.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() })
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Difficulty", fontWeight = FontWeight.SemiBold)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Difficulty.entries.forEach { diff ->
                        FilterChip(
                            selected = (difficulty == diff),
                            onClick = { difficulty = diff },
                            label = { Text(diff.name.lowercase().replaceFirstChar { it.uppercase() }) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { onStartQuiz(category, difficulty, name) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepPurple),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("START QUIZ", modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

@Composable
fun QuizScreen(
    questions: List<Question>,
    onQuizFinished: (Int) -> Unit
) {
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var selectedOptionIndex by remember { mutableIntStateOf(-1) }
    var timeLeft by remember { mutableIntStateOf(30) }

    LaunchedEffect(currentQuestionIndex) {
        timeLeft = 30
        while (timeLeft > 0) {
            delay(1.seconds)
            timeLeft--
        }
    }

    if (questions.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No questions found.")
        }
        return
    }

    val currentQuestion = questions[currentQuestionIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Basic Quiz App",
            style = MaterialTheme.typography.headlineMedium,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Score - ${score.toString().padStart(2, '0')}", fontWeight = FontWeight.Bold)
            Text(text = "${currentQuestionIndex + 1}/${questions.size}", fontWeight = FontWeight.Bold)
        }

        Text(
            text = "Time: $timeLeft",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Q.${currentQuestionIndex + 1}. ${currentQuestion.text}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        currentQuestion.options.forEachIndexed { index, option ->
            OptionItem(
                text = option,
                letter = ('A' + index).toString(),
                isSelected = selectedOptionIndex == index,
                onSelect = { selectedOptionIndex = index }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (selectedOptionIndex == currentQuestion.correctAnswerIndex) {
                    score++
                }
                if (currentQuestionIndex < questions.size - 1) {
                    currentQuestionIndex++
                    selectedOptionIndex = -1
                } else {
                    onQuizFinished(score)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DeepPurple),
            shape = RoundedCornerShape(24.dp),
            enabled = selectedOptionIndex != -1
        ) {
            Text("NEXT QUESTION", modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun OptionItem(
    text: String,
    letter: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable { onSelect() }
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) DeepPurple else Color.LightGray,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(selectedColor = DeepPurple)
        )
        Text(text = text, modifier = Modifier.weight(1f))
        
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(if (isSelected) DeepPurple else SkyBlue, RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = letter, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ResultScreen(
    score: Int,
    total: Int,
    userName: String,
    onTryAgain: () -> Unit
) {
    val accuracy = if (total > 0) (score.toFloat() / total * 100).toInt() else 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier.clickable { onTryAgain() }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Quiz Results", style = MaterialTheme.typography.titleLarge)
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = Color(0xFFFFD700)
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                if (userName.isNotEmpty()) {
                    Text(
                        text = "Great job, $userName!",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Gray
                    )
                }

                val (remark, remarkColor) = when {
                    accuracy == 100 -> "Perfect!" to CorrectGreen
                    accuracy >= 75 -> "Excellent!" to CorrectGreen
                    accuracy >= 50 -> "Good Job!" to Color(0xFFFFA500) // Orange
                    accuracy >= 25 -> "Fair!" to Color(0xFFFFA500)
                    else -> "Keep Trying!" to Color.Red
                }

                Text(
                    text = remark,
                    style = MaterialTheme.typography.headlineLarge,
                    color = remarkColor,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(32.dp))

                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .border(12.dp, remarkColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$score/$total",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "SCORE", style = MaterialTheme.typography.labelLarge)
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem(label = "$accuracy%", subLabel = "ACCURACY")
                    StatItem(label = "10", subLabel = "STREAK")
                    StatItem(label = "3", subLabel = "QUIZ")
                }

                Spacer(modifier = Modifier.weight(1f))

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { /* Review Questions */ }
                        .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp)),
                    color = Color.White
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(text = "Review Questions", fontWeight = FontWeight.Bold)
                            Text(text = "$total questions answered", style = MaterialTheme.typography.labelSmall)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = "⌄", fontSize = 24.sp)
                    }
                }
            }
        }

        Button(
            onClick = onTryAgain,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DeepPurple),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Try Again", modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun StatItem(label: String, subLabel: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontWeight = FontWeight.Bold, color = Color.Gray)
        Text(text = subLabel, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    QuizApplicationTheme {
        HomeScreen(onStartQuiz = { _, _, _ -> })
    }
}

@Preview(showBackground = true)
@Composable
fun QuizPreview() {
    QuizApplicationTheme {
        QuizScreen(
            questions = sampleQuestions,
            onQuizFinished = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResultPreview() {
    QuizApplicationTheme {
        ResultScreen(score = 10, total = 10, userName = "Test", onTryAgain = {})
    }
}
