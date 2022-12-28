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
        setViewAccordingStatus(binding, StatusForView.AllSeatsAreFree)

        binding.addPassenger.setOnClickListener {
            counter++
            setViewAccordingStatus(binding, StatusForView.SeatsLeft)
            if (maxSeats - counter == 0) {
                setViewAccordingStatus(binding, StatusForView.AllSeatsAreOccupied)
            }
        }
        binding.removePassenger.setOnClickListener {
            counter--
            setViewAccordingStatus(binding, StatusForView.SeatsLeft)
            if (counter == 0)
                setViewAccordingStatus(binding, StatusForView.AllSeatsAreFree)
        }
        binding.reset.setOnClickListener {
            counter = 0
            setViewAccordingStatus(binding, StatusForView.AllSeatsAreFree)
        }
    }

    private fun setViewAccordingStatus(
        binding: ActivityMainBinding,
        status: StatusForView,
    ) {
        when (status) {
            is StatusForView.AllSeatsAreFree -> {
                binding.passengersInfo.setTextAppearance(R.style.AllSeatsAreFree)
                binding.passengersInfo.text = this.getString(R.string.allSeatsAreFree)
                binding.addPassenger.isEnabled = true
                binding.removePassenger.isEnabled = false
                binding.reset.visibility = View.INVISIBLE
            }
            is StatusForView.AllSeatsAreOccupied -> {
                binding.passengersInfo.text = this.getString(R.string.allSeatsAreOccupied)
                binding.passengersInfo.setTextAppearance(R.style.AllSeatsAreOccupied)
                binding.reset.visibility = View.VISIBLE
                binding.addPassenger.isEnabled = false
            }
            is StatusForView.SeatsLeft -> {
                binding.passengersInfo.text =
                    this.getString(R.string.seatsLeft) + " ${this.maxSeats - this.counter}"
                binding.removePassenger.isEnabled = this.counter != 0
                binding.addPassenger.isEnabled = this.maxSeats != 0
                binding.passengersInfo.setTextAppearance(R.style.SeatsLeft)
                binding.reset.visibility = View.INVISIBLE
            }
        }
    }
}
