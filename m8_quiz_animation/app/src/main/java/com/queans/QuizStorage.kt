package com.queans

class QuizStorage {
    companion object {
        val questions = listOf(R.string.question1, R.string.question2, R.string.question3, R.string.question4)
        val answers = listOf(
            listOf(R.string.answer1_1, R.string.answer1_2, R.string.answer1_3, R.string.answer1_4),
            listOf(R.string.answer2_1, R.string.answer2_2, R.string.answer2_3, R.string.answer2_4),
            listOf(R.string.answer3_1, R.string.answer3_2, R.string.answer3_3, R.string.answer3_4),
        )
        val correctAnswers = listOf(3, 3, 4, "хомяк")
    }
}