package com.queans

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        binding.question1.changeQuestionText(getString(QuizStorage.questions[0]))
        binding.question2.changeQuestionText(getString(QuizStorage.questions[1]))
        binding.question3.changeQuestionText(getString(QuizStorage.questions[2]))

        binding.question1.changeAnswersText(QuizStorage.answers[0])
        binding.question2.changeAnswersText(QuizStorage.answers[1])
        binding.question3.changeAnswersText(QuizStorage.answers[2])

        binding.sendButton.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("ans1", binding.question1.getAnswer())
                putInt("ans2", binding.question2.getAnswer())
                putInt("ans3", binding.question3.getAnswer())
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