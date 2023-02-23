package com.example.mvvm.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mvvm.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.searchButton.setOnClickListener {
            binding.searchButton.isEnabled = false
            viewModel.onSearchClick(binding.editTextSearch.text.toString())
        }
        binding.editTextSearch.doOnTextChanged { text, start, count, after ->
            binding.searchButton.isEnabled = text.toString().length >= 3
        }
        viewLifecycleOwner.lifecycleScope
            .launchWhenStarted {
                viewModel.state.collect { state ->
                    when (state) {
                        State.Start -> {}
                        State.Loading -> {
                            binding.progressCircular.isVisible = true
                            binding.message.isVisible = false
                        }
                        is State.Success -> {
                            binding.message.text =
                                "По запросу \"${state.text}\" ничего не найдено"
                            binding.searchButton.isEnabled = true
                            binding.progressCircular.isVisible = false
                            binding.message.isVisible = true
                        }
                    }
                }
            }

        return binding.root
    }
}