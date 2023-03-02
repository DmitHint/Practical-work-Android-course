package com.example.retrofit.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.retrofit.R
import com.example.retrofit.ResultsUserModel
import com.example.retrofit.RetrofitInstance
import com.example.retrofit.User
import com.example.retrofit.databinding.FragmentMainBinding
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.E

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater)

        binding.refreshButton.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                val user = viewModel.searchUser()
                showUser(user)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state) {
                    State.Start -> {
                        showProgress()
                        this@MainFragment.user = viewModel.searchUser()
                    }
                    State.Loading -> {
                        showProgress()
                    }
                    State.Success -> {
                        hideProgress()
                        showUser(this@MainFragment.user)
                    }
                }
            }
        }
        return binding.root
    }

    private fun showProgress() {
        binding.nameFirstField.isVisible = false
        binding.nameFirstValue.isVisible = false
        binding.nameLastValue.isVisible = false
        binding.nameLastField.isVisible = false
        binding.countryField.isVisible = false
        binding.countryValue.isVisible = false
        binding.cityField.isVisible = false
        binding.cityValue.isVisible = false
        binding.emailField.isVisible = false
        binding.emailValue.isVisible = false
        binding.phoneField.isVisible = false
        binding.phoneValue.isVisible = false
        binding.picture.isVisible = false
        binding.refreshButton.isVisible = false

        binding.progressCircular.isVisible = true
    }

    private fun hideProgress() {
        binding.nameFirstField.isVisible = true
        binding.nameFirstValue.isVisible = true
        binding.nameLastValue.isVisible = true
        binding.nameLastField.isVisible = true
        binding.countryField.isVisible = true
        binding.countryValue.isVisible = true
        binding.cityField.isVisible = true
        binding.cityValue.isVisible = true
        binding.emailField.isVisible = true
        binding.emailValue.isVisible = true
        binding.phoneField.isVisible = true
        binding.phoneValue.isVisible = true
        binding.picture.isVisible = true
        binding.refreshButton.isVisible = true

        binding.progressCircular.isVisible = false
    }

    private fun showUser(user: User?) {
        if (user != null) {
            Glide.with(this@MainFragment)
                .load(user.picture.large)
                .fitCenter()
                .into(binding.picture)
            binding.nameFirstValue.text = user.name.first
            binding.nameLastValue.text = user.name.last
            binding.countryValue.text = user.location.country
            binding.cityValue.text = user.location.city
            binding.emailValue.text = user.email
            binding.phoneValue.text = user.phone
        }
    }
}