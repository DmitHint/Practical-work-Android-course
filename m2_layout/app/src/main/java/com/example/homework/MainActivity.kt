package com.example.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homework.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.num1.setUserName("Пользователь 1")
        binding.num1.setDescription("Текст поста с очень длинной и интересной историей")

        binding.num2.setUserName("Пользователь 2")
        binding.num2.setDescription("Текст поста с короткой историей")

        binding.num3.setUserName("Пользователь 3")
        binding.num3.setDescription("Текст...и все")

        binding.num4.setUserName("верхняя строчка, настроенная из кода")
        binding.num4.setDescription("нижняя строчка, настроенная из кода")
    }
}