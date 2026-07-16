package com.example.quizapplication

enum class Category {
    GENERAL_KNOWLEDGE, SCIENCE, SPORTS, TECHNOLOGY
}

enum class Difficulty {
    EASY, MEDIUM, HARD
}

data class Question(
    val text: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val category: Category,
    val difficulty: Difficulty
)

val sampleQuestions = listOf(
    // General Knowledge - Easy
    Question(
        text = "What is the capital of France?",
        options = listOf("London", "Berlin", "Paris", "Madrid"),
        correctAnswerIndex = 2,
        category = Category.GENERAL_KNOWLEDGE,
        difficulty = Difficulty.EASY
    ),
    Question(
        text = "Which is the largest country by area?",
        options = listOf("USA", "China", "Russia", "India"),
        correctAnswerIndex = 2,
        category = Category.GENERAL_KNOWLEDGE,
        difficulty = Difficulty.EASY
    ),
    Question(
        text = "Who wrote 'Romeo and Juliet'?",
        options = listOf("Charles Dickens", "William Shakespeare", "Mark Twain", "Leo Tolstoy"),
        correctAnswerIndex = 1,
        category = Category.GENERAL_KNOWLEDGE,
        difficulty = Difficulty.EASY
    ),
    Question(
        text = "What is the currency of Japan?",
        options = listOf("Yuan", "Yen", "Won", "Dollar"),
        correctAnswerIndex = 1,
        category = Category.GENERAL_KNOWLEDGE,
        difficulty = Difficulty.EASY
    ),

    // Science - Easy
    Question(
        text = "Which planet is known as the Red Planet?",
        options = listOf("Venus", "Mars", "Jupiter", "Saturn"),
        correctAnswerIndex = 1,
        category = Category.SCIENCE,
        difficulty = Difficulty.EASY
    ),
    Question(
        text = "What is the boiling point of water at sea level?",
        options = listOf("50°C", "80°C", "100°C", "120°C"),
        correctAnswerIndex = 2,
        category = Category.SCIENCE,
        difficulty = Difficulty.EASY
    ),
    Question(
        text = "Which gas do plants absorb from the atmosphere?",
        options = listOf("Oxygen", "Nitrogen", "Carbon Dioxide", "Hydrogen"),
        correctAnswerIndex = 2,
        category = Category.SCIENCE,
        difficulty = Difficulty.EASY
    ),
    Question(
        text = "What is the hardest natural substance on Earth?",
        options = listOf("Gold", "Iron", "Diamond", "Quartz"),
        correctAnswerIndex = 2,
        category = Category.SCIENCE,
        difficulty = Difficulty.EASY
    ),

    // Sports - Easy
    Question(
        text = "How many players are there in a standard soccer team?",
        options = listOf("9", "10", "11", "12"),
        correctAnswerIndex = 2,
        category = Category.SPORTS,
        difficulty = Difficulty.EASY
    ),
    Question(
        text = "In which sport is the term 'Home Run' used?",
        options = listOf("Cricket", "Baseball", "Tennis", "Golf"),
        correctAnswerIndex = 1,
        category = Category.SPORTS,
        difficulty = Difficulty.EASY
    ),
    Question(
        text = "Which country hosts the Tour de France?",
        options = listOf("Italy", "Spain", "France", "Germany"),
        correctAnswerIndex = 2,
        category = Category.SPORTS,
        difficulty = Difficulty.EASY
    ),
    Question(
        text = "How many rings are there on the Olympic flag?",
        options = listOf("4", "5", "6", "7"),
        correctAnswerIndex = 1,
        category = Category.SPORTS,
        difficulty = Difficulty.EASY
    ),

    // Technology - Easy
    Question(
        text = "Who is the co-founder of Microsoft?",
        options = listOf("Steve Jobs", "Bill Gates", "Elon Musk", "Mark Zuckerberg"),
        correctAnswerIndex = 1,
        category = Category.TECHNOLOGY,
        difficulty = Difficulty.EASY
    ),
    Question(
        text = "What does 'WWW' stand for?",
        options = listOf("World Wide Web", "Web World Wide", "World Web Wide", "Wide World Web"),
        correctAnswerIndex = 0,
        category = Category.TECHNOLOGY,
        difficulty = Difficulty.EASY
    ),
    Question(
        text = "Which company developed the Android OS?",
        options = listOf("Apple", "Microsoft", "Google", "Nokia"),
        correctAnswerIndex = 2,
        category = Category.TECHNOLOGY,
        difficulty = Difficulty.EASY
    ),
    Question(
        text = "What is the main brain of a computer?",
        options = listOf("RAM", "GPU", "CPU", "Hard Disk"),
        correctAnswerIndex = 2,
        category = Category.TECHNOLOGY,
        difficulty = Difficulty.EASY
    )
)
