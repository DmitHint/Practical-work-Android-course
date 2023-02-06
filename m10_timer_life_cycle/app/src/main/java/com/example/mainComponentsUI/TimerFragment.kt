package com.example.mainComponentsUI

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.mainComponentsUI.databinding.TimerFragmentBinding
import kotlinx.coroutines.*


private const val TIME = "time"
private const val IS_STARTED = "isStarted"
private const val TIME_ON_START = "timeOnStart"

private const val DEF_TIME_VALUE = 5
private const val DEF_IS_STARTED_VALUE = false

class TimerFragment : Fragment() {
    private var time: Int = DEF_TIME_VALUE
    private var isStarted: Boolean = DEF_IS_STARTED_VALUE
    private var timeOnStart: Int? = null

    private var _binding: TimerFragmentBinding? = null
    private val binding get() = _binding!!

    private val scope = CoroutineScope(Job() + Dispatchers.Main)
    private var curTime: Job = scope.launch {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            time = it.getInt(TIME, DEF_TIME_VALUE)
            isStarted = it.getBoolean(IS_STARTED, DEF_IS_STARTED_VALUE)
            timeOnStart = it.getInt(TIME_ON_START)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(TIME, this.binding.time.text.toString().toInt())
        outState.putBoolean(IS_STARTED, this.isStarted)
        if (timeOnStart == null) {
            timeOnStart = time
        }
        outState.putInt(TIME_ON_START, timeOnStart!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TimerFragmentBinding.inflate(inflater)
        binding.time.text = time.toString()
        binding.progressCircular.progress = 100

        if (isStarted) {
            countDown()
        }

        binding.slider.addOnChangeListener { _, value, _ ->
            if (!isStarted)
                binding.time.text = value.toInt().toString()
        }

        binding.themeButtonDark.setOnClickListener {
            binding.themeButtonLight.visibility = View.VISIBLE
            binding.themeButtonDark.visibility = View.INVISIBLE
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        binding.themeButtonLight.setOnClickListener {
            binding.themeButtonLight.visibility = View.INVISIBLE
            binding.themeButtonDark.visibility = View.VISIBLE
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        binding.timerButton.setOnClickListener {
            isStarted = !isStarted
            time = binding.time.text.toString().toInt()
            if (isStarted) {
                timeOnStart = time
                countDown()
            } else {
                curTime.cancel()
                makeDefault()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkNightTheme()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.curTime.cancel()
        _binding = null
    }

    private fun checkNightTheme() {
        when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_NO -> {
                binding.themeButtonLight.visibility = View.INVISIBLE
                binding.themeButtonDark.visibility = View.VISIBLE
            }
            AppCompatDelegate.MODE_NIGHT_YES -> {
                binding.themeButtonLight.visibility = View.VISIBLE
                binding.themeButtonDark.visibility = View.INVISIBLE
            }
            AppCompatDelegate.MODE_NIGHT_UNSPECIFIED -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.themeButtonLight.visibility = View.INVISIBLE
                binding.themeButtonDark.visibility = View.VISIBLE
            }
        }
    }

    private fun countDown() {
        binding.timerButton.setText(R.string.stop)
        binding.slider.isEnabled = false
        if (timeOnStart == null) {
            timeOnStart = time
        }
        binding.progressCircular.progress = 100 - (100 / timeOnStart!!) * (timeOnStart!! - time)
        curTime = scope.launch {
            while (binding.time.text.toString().toInt() - 1 >= 0) {
                delay(1000)
                binding.time.text = (binding.time.text.toString().toInt() - 1).toString()
                binding.progressCircular.progress -= 100 / timeOnStart!!
            }
            makeDefault()
        }
    }
    private fun makeDefault(){
        binding.slider.isEnabled = true
        binding.progressCircular.progress = 100
        binding.time.text = binding.slider.value.toInt().toString()
        binding.timerButton.setText(R.string.start)
        Toast.makeText(
            context,
            getString(R.string.task_finished),
            Toast.LENGTH_SHORT
        ).show()
        timeOnStart = null
        isStarted = false
    }
}