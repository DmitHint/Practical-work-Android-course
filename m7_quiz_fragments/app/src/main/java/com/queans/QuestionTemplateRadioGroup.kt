package com.queans

import android.content.Context
import android.provider.Settings.Global.getString
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.queans.databinding.QuestionTemplateRadioBinding

class QuestionTemplateRadioGroup
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = QuestionTemplateRadioBinding.inflate(LayoutInflater.from(context))
    private var answer = 0

    init {
        addView(binding.root)
        binding.answers.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.answer1 -> answer = 1
                R.id.answer2 -> answer = 2
                R.id.answer3 -> answer = 3
                R.id.answer4 -> answer = 4
            }
        }
    }

    fun changeQuestionText(text: String) {
        binding.question.text = text
    }

    fun getAnswer(): Int = answer


    fun changeAnswersText(answers: List<Int>) {
        binding.answer1.text = this.resources.getString(answers[0])
        binding.answer2.text = this.resources.getString(answers[1])
        binding.answer3.text = this.resources.getString(answers[2])
        binding.answer4.text = this.resources.getString(answers[3])
    }
}