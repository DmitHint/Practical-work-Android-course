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
import kotlin.random.Random
import org.koin.android.ext.android.get

class BicycleFragment : Fragment() {


    private lateinit var binding: FragmentBicycleBinding

    private val viewModelDagger by viewModels<BicycleViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                BicycleViewModel(App.daggerComponent.bicycleFactory()) as T
        }
    }

    private val viewModelKoin by viewModels<BicycleViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return BicycleViewModel(bicycleFactory = get()) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBicycleBinding.inflate(layoutInflater)


        binding.daggerButton.setOnClickListener {
            val bicycle = viewModelDagger.bicycleFactory.build(getRandomLogo(), getRandomColor())
            Toast.makeText(
                activity, "Logo: ${bicycle.logo}, Color: ${bicycle.frame.color}, " +
                        "Frame code: ${bicycle.frame.serialNumber}, " +
                        "Wheels code: ${bicycle.wheels.first.serialNumber} & ${bicycle.wheels.second.serialNumber}",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.koinButton.setOnClickListener {
            val bicycle = viewModelKoin.bicycleFactory.build(getRandomLogo(), getRandomColor())
            Toast.makeText(
                activity, "Logo: ${bicycle.logo}, Color: ${bicycle.frame.color}, " +
                        "Frame code: ${bicycle.frame.serialNumber}, " +
                        "Wheels code: ${bicycle.wheels.first.serialNumber} & ${bicycle.wheels.second.serialNumber}",
                Toast.LENGTH_SHORT
            ).show()
        }

        return binding.root
    }

    private fun getRandomColor(): Int = Random(System.currentTimeMillis()).nextInt(256)

    private fun getRandomLogo(): String {
        val alphabet = "abcdefghijklmnopqrstuvwxyz"
        val random = Random(System.currentTimeMillis())

        val randomLetters = (1..3)
            .map { alphabet[random.nextInt(alphabet.length)] }
            .joinToString("")

        return randomLetters.uppercase()
    }
}
