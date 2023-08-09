package com.example.dependency

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dependency.databinding.FragmentBicycleBinding

class BicycleFragment : Fragment() {

    private lateinit var binding: FragmentBicycleBinding
    private lateinit var bicycleFactory: BicycleFactory

    private val viewModel by viewModels<BicycleViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                BicycleViewModel(App.daggerComponent.bicycleFactory()) as T
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBicycleBinding.inflate(layoutInflater)

        bicycleFactory = BicycleFactory(
            App.daggerComponent.wheelDealer(),
            App.frameFactory,
        )


        binding.daggerButton.setOnClickListener {
            Toast.makeText(activity, "Dagger", Toast.LENGTH_SHORT).show()
        }

        binding.koinButton.setOnClickListener {
            Toast.makeText(activity, "Koin", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}