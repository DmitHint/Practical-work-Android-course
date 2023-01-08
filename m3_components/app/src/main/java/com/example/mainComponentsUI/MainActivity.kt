package com.example.mainComponentsUI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.slider.Slider
import kotlinx.coroutines.*

var firstRun = true

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val themeButtonDark = findViewById<ImageButton>(R.id.themeButtonDark)
        val themeButtonLight = findViewById<ImageButton>(R.id.themeButtonLight)
        val timerButton = findViewById<Button>(R.id.timerButton)

        val progressCircular = findViewById<ProgressBar>(R.id.progressCircular)
        val slider = findViewById<Slider>(R.id.slider)
        val time = findViewById<TextView>(R.id.time)

        if (firstRun) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            firstRun = false
        } else {
            when(AppCompatDelegate.getDefaultNightMode()){
                AppCompatDelegate.MODE_NIGHT_NO -> {
                    themeButtonLight.visibility = View.INVISIBLE
                    themeButtonDark.visibility = View.VISIBLE
                }
                AppCompatDelegate.MODE_NIGHT_YES -> {
                    themeButtonLight.visibility = View.VISIBLE
                    themeButtonDark.visibility = View.INVISIBLE
                }
            }
        }

        time.text = slider.value.toInt().toString()

        slider.addOnChangeListener { _, value, _ ->
            time.text = value.toInt().toString()
        }

        themeButtonDark.setOnClickListener {
            themeButtonLight.visibility = View.VISIBLE
            themeButtonDark.visibility = View.INVISIBLE
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        themeButtonLight.setOnClickListener {
            themeButtonLight.visibility = View.INVISIBLE
            themeButtonDark.visibility = View.VISIBLE
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        var isStarted = false
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        var curTime: Job = scope.launch {}
        timerButton.setOnClickListener {
            isStarted = !isStarted
            val valueOnStart = time.text.toString().toInt()
            if (isStarted) {
                themeButtonDark.isEnabled = false
                themeButtonLight.isEnabled = false
                timerButton.setText(R.string.stop)
                slider.isEnabled = false
                curTime = scope.launch {
                    while (time.text.toString().toInt() - 1 >= 0) {
                        delay(1000)
                        time.text = (time.text.toString().toInt() - 1).toString()
                        progressCircular.progress -= 100 / valueOnStart
                    }
                    slider.isEnabled = true
                    progressCircular.progress = 100
                    time.text = slider.value.toInt().toString()
                    timerButton.setText(R.string.start)
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.task_finished),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                themeButtonDark.isEnabled = true
                themeButtonLight.isEnabled = true
                curTime.cancel()
                slider.isEnabled = true
                progressCircular.progress = 100
                timerButton.setText(R.string.start)
                time.text = slider.value.toInt().toString()
                Toast.makeText(this, getString(R.string.task_finished), Toast.LENGTH_SHORT).show()
            }
        }
    }
}