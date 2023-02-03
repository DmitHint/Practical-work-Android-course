package com.queans

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.queans.databinding.FragmentResultsBinding
import java.util.*

private const val ARG_ANS1 = "ans1"
private const val ARG_ANS2 = "ans2"
private const val ARG_ANS3 = "ans3"
private const val ARG_ANS4 = "ans4"

class ResultsFragment : Fragment() {
    private var ans1: Int? = null
    private var ans2: Int? = null
    private var ans3: Int? = null
    private var ans4: String? = null
    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ans1 = it.getInt(ARG_ANS1)
            ans2 = it.getInt(ARG_ANS2)
            ans3 = it.getInt(ARG_ANS3)
            ans4 = it.getString(ARG_ANS4)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultsBinding.inflate(inflater)
        binding.startOverButton.setOnClickListener {
            findNavController().navigate(R.id.action_ResultsFragment_to_QuizFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var progress = 0.0
        val countCorrectAnswers = listOf(
            ans1 == QuizStorage.correctAnswers[0],
            ans2 == QuizStorage.correctAnswers[1],
            ans3 == QuizStorage.correctAnswers[2],
            ans4 == getString(QuizStorage.correctAnswers[3]).lowercase().trim(),
        ).count { it }

        if (countCorrectAnswers != 0)
            progress =
                100 * (countCorrectAnswers.toDouble() / QuizStorage.questions.count().toDouble())

        var progressBarAnimator =
            ObjectAnimator.ofInt(binding.progressCircular, "progress", progress.toInt()).apply {
                duration = 3000
                interpolator = DecelerateInterpolator(0.8f)
                start()
            }
        var textBarAnimator =
            ValueAnimator.ofFloat(0f, progress.toFloat()).apply {
                duration = 3000
                interpolator = DecelerateInterpolator(0.8f)
                start()
            }.addUpdateListener {
                if (context != null) {
                    val text =
                        String.format("%.1f", it.animatedValue) + getString(R.string.percent_sign)
                    binding.progressText.text = text
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}