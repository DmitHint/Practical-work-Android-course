package com.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.homework.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var counter = 0
    private val maxSeats = 50
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.passengersInfo.text = getString(R.string.allSeatsAreFree)
        binding.removePassenger.isEnabled = false

        binding.addPassenger.setOnClickListener {
            counter++
            binding.passengersInfo.text = "${getString(R.string.seatsLeft)} ${maxSeats - counter}"
            binding.passengersInfo.setTextAppearance(R.style.SeatsLeft)
            binding.removePassenger.isEnabled = true
            binding.reset.visibility = View.INVISIBLE
            if (maxSeats - counter == 0) {
                binding.passengersInfo.text = getString(R.string.allSeatsAreOccupied)
                binding.passengersInfo.setTextAppearance(R.style.AllSeatsAreOccupied)
                binding.reset.visibility = View.VISIBLE
                binding.addPassenger.isEnabled = false
            }
        }
        binding.removePassenger.setOnClickListener {
            counter--
            binding.addPassenger.isEnabled = true
            binding.passengersInfo.text = "${getString(R.string.seatsLeft)} ${maxSeats - counter}"
            binding.passengersInfo.setTextAppearance(R.style.SeatsLeft)
            binding.reset.visibility = View.INVISIBLE
            if (counter == 0) {
                binding.passengersInfo.text = getString(R.string.allSeatsAreFree)
                binding.passengersInfo.setTextAppearance(R.style.AllSeatsAreFree)
                binding.removePassenger.isEnabled = false
            }
        }
        binding.reset.setOnClickListener {
            counter = 0
            binding.passengersInfo.text = getString(R.string.allSeatsAreFree)
            binding.passengersInfo.setTextAppearance(R.style.AllSeatsAreFree)
            binding.addPassenger.isEnabled = true
            binding.removePassenger.isEnabled = false
            binding.reset.visibility = View.INVISIBLE
        }
    }
}