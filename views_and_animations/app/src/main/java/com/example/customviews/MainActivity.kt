package com.example.customviews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.customviews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.startStopButton.setOnClickListener {
            if (isRunning) {
                binding.startStopButton.text = "Start"
                binding.clock.stop()
            } else {
                binding.startStopButton.text = "Stop"
                binding.clock.start()
            }
        }

        binding.clock.addUpdateListener {
            isRunning = it.isRunning
            binding.time.text = timeToString(it.time)
        }

        binding.resetButton.setOnClickListener {
            binding.clock.reset()
        }
    }

    private fun timeToString(time: Long): String {
        val timeInSeconds = time / 1000
        val hours = timeInSeconds / 3600
        val minutes = (timeInSeconds % 3600) / 60
        val seconds = timeInSeconds % 60
        return "%02d:%02d:%02d".format(hours, minutes, seconds)
    }
}