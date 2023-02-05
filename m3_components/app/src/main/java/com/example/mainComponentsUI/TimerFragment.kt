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


private const val PROGRESS = "progress"

class TimerFragment : Fragment() {
    private var _binding: TimerFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        savedInstanceState?.let { bundle ->
            bundle.getInt(PROGRESS, binding.slider.value.toInt())
        }

        _binding = TimerFragmentBinding.inflate(inflater)
        binding.time.text = binding.slider.value.toInt().toString()

        binding.slider.addOnChangeListener { _, value, _ ->
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

        var isStarted = false
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        var curTime: Job = scope.launch {}
        binding.timerButton.setOnClickListener {
            isStarted = !isStarted
            val valueOnStart = binding.time.text.toString().toInt()
            if (isStarted) {
                binding.themeButtonDark.isEnabled = false
                binding.themeButtonLight.isEnabled = false
                binding.timerButton.setText(R.string.stop)
                binding.slider.isEnabled = false
                curTime = scope.launch {
                    while (binding.time.text.toString().toInt() - 1 >= 0) {
                        delay(1000)
                        binding.time.text = (binding.time.text.toString().toInt() - 1).toString()
                        binding.progressCircular.progress -= 100 / valueOnStart
                    }
                    binding.slider.isEnabled = true
                    binding.progressCircular.progress = 100
                    binding.time.text = binding.slider.value.toInt().toString()
                    binding.timerButton.setText(R.string.start)
                    Toast.makeText(
                        context,
                        getString(R.string.task_finished),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                binding.themeButtonDark.isEnabled = true
                binding.themeButtonLight.isEnabled = true
                curTime.cancel()
                binding.slider.isEnabled = true
                binding.progressCircular.progress = 100
                binding.timerButton.setText(R.string.start)
                binding.time.text = binding.slider.value.toInt().toString()
                Toast.makeText(context, getString(R.string.task_finished), Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("progress", this.binding.progressCircular.progress)
        super.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.let {

        }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}