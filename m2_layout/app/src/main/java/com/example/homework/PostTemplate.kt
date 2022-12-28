package com.example.homework

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.example.homework.databinding.PostTemplateBinding

class PostTemplate
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private val binding = PostTemplateBinding.inflate(LayoutInflater.from(context))
    init {
        addView(binding.root)
    }
    fun setUserName(newText: String){
        binding.userName.text = newText
    }
    fun setDescription(newText: String){
        binding.description.text = newText
    }
}