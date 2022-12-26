package com.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.homework.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var counter = 0
    private val maxSeats = Constants.maxCapacity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewAccordingStatus(
            binding,
            StatusForView.AllSeatsAreFree,
            getString(R.string.allSeatsAreFree)
        )

        binding.addPassenger.setOnClickListener {
            counter++
            setViewAccordingStatus(
                binding,
                StatusForView.SeatsLeft,
                getString(R.string.seatsLeft),
                maxSeats,
                counter
            )
            if (maxSeats - counter == 0) {
                setViewAccordingStatus(
                    binding,
                    StatusForView.AllSeatsAreOccupied,
                    getString(R.string.allSeatsAreOccupied)
                )
            }
        }
        binding.removePassenger.setOnClickListener {
            counter--
            setViewAccordingStatus(
                binding,
                StatusForView.SeatsLeft,
                getString(R.string.seatsLeft),
                maxSeats,
                counter
            )
            if (counter == 0)
                setViewAccordingStatus(
                    binding,
                    StatusForView.AllSeatsAreFree,
                    getString(R.string.allSeatsAreFree)
                )
        }
        binding.reset.setOnClickListener {
            counter = 0
            setViewAccordingStatus(
                binding,
                StatusForView.AllSeatsAreFree,
                getString(R.string.allSeatsAreFree)
            )
        }
    }
}

fun setViewAccordingStatus(
    binding: ActivityMainBinding,
    status: StatusForView,
    textForView: String,
    maxSeats: Int = 0,
    counter: Int = 0
) {
    binding.passengersInfo.text = textForView
    when (status) {
        is StatusForView.AllSeatsAreFree -> {
            binding.passengersInfo.setTextAppearance(R.style.AllSeatsAreFree)
            binding.addPassenger.isEnabled = true
            binding.removePassenger.isEnabled = false
            binding.reset.visibility = View.INVISIBLE
        }
        is StatusForView.AllSeatsAreOccupied -> {
            binding.passengersInfo.setTextAppearance(R.style.AllSeatsAreOccupied)
            binding.reset.visibility = View.VISIBLE
            binding.addPassenger.isEnabled = false
        }
        is StatusForView.SeatsLeft -> {
            binding.passengersInfo.text = textForView + " ${maxSeats - counter}"
            binding.removePassenger.isEnabled = counter != 0
            binding.addPassenger.isEnabled = maxSeats != 0
            binding.passengersInfo.setTextAppearance(R.style.SeatsLeft)
            binding.reset.visibility = View.INVISIBLE
        }
    }
}