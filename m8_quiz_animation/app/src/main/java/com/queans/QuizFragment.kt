package com.queans

import android.animation.AnimatorInflater
import android.animation.ValueAnimator
import android.app.ActivityOptions
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.queans.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizBinding.inflate(inflater)
        val questionsRadioGroup =
            listOf(binding.question1, binding.question2, binding.question3)
        val questionsPhoto =
            listOf(binding.question4)
        questionsRadioGroup.forEachIndexed { index, question ->
            question.changeQuestionText(getString(QuizStorage.questions[index]))
            (AnimatorInflater.loadAnimator(context, R.animator.alpha_question)).apply {
                setTarget(question)
                start()
            }
        }
        questionsPhoto[0].changeQuestionText(getString(QuizStorage.questions[3]))
        (AnimatorInflater.loadAnimator(context, R.animator.alpha_question)).apply {
            setTarget(questionsPhoto[0])
            start()
        }

        binding.question1.changeAnswersText(QuizStorage.answers[0])
        binding.question2.changeAnswersText(QuizStorage.answers[1])
        binding.question3.changeAnswersText(QuizStorage.answers[2])

        binding.sendButton.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("ans1", binding.question1.getAnswer())
                putInt("ans2", binding.question2.getAnswer())
                putInt("ans3", binding.question3.getAnswer())
                putString("ans4", binding.question4.getAnswer())
            }
            findNavController().navigate(R.id.action_QuizFragment_to_ResultsFragment, args = bundle)
        }
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_QuizFragment_to_StartFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}