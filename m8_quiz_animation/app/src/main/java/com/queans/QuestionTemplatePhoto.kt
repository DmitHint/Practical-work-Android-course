package com.queans

import android.content.Context
import android.provider.Settings.Global.getString
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import com.queans.databinding.QuestionTemplatePhotoBinding

class QuestionTemplatePhoto
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private val binding = QuestionTemplatePhotoBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)
    }


    fun changeQuestionText(text: String) {
        binding.question.text = text
    }

    fun showImage() {
        binding.image
    }

    fun getAnswer(): String = binding.editText.text.toString()
}