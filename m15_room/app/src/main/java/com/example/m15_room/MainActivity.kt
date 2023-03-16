package com.example.m15_room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.m15_room.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val wordDao = (application as App).db.wordDao()
                return MainViewModel(wordDao) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBtn.setOnClickListener {
            if (!binding.textInput.text.toString().contains("[a-zA-Zа-яА-Я-]".toRegex())
            ) {
                binding.textInput.error = "Допустимы только буквы и дефисы"
            } else {
                viewModel.onAddBtn(binding.textInput.text.toString())
            }
        }
        binding.clearBtn.setOnClickListener { viewModel.onDeleteBtn() }

        lifecycleScope.launchWhenCreated {
            viewModel.allWords
                .collect { words ->
                    if (words.size < 6)
                        binding.textView
                            .text = words.joinToString(
                            separator = "\r\n"
                        )
                }
        }
    }
}