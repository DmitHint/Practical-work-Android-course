package com.example.clean.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.clean.di.DaggerAppComponent
import com.example.clean.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by viewModels {
        DaggerAppComponent.create().mainViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.refreshBtn.setOnClickListener {
            viewModel.reloadUsefulActivity()
            binding.textActivity.text = viewModel.activity.activity
        }
        viewLifecycleOwner.lifecycleScope
            .launchWhenStarted {
                viewModel.state.collect { state ->
                    when (state) {
                        State.Start -> {
                            viewModel.reloadUsefulActivity()
                        }
                        State.Loading -> {
                            viewModel.reloadUsefulActivity()
                            binding.progressCircular.isVisible = true
                            binding.textActivity.isVisible = false
                        }
                        State.Success -> {
                            binding.progressCircular.isVisible = false
                            binding.textActivity.isVisible = true
                            binding.textActivity.text = viewModel.activity.activity
                        }
                    }
                }
            }
    }

}
