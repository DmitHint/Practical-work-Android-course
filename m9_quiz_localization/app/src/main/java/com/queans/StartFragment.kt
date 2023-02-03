package com.queans

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialDialogs
import com.google.android.material.snackbar.Snackbar
import com.queans.databinding.FragmentStartBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    private val calendar = Calendar.getInstance()

//    private val dateFormat = SimpleDateFormat("dd-MM-yyyy")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater)
        binding.continueButton.setOnClickListener {
            findNavController().navigate(R.id.action_StartFragment_to_QuizFragment)
        }
        binding.calendar.setOnClickListener {
            val dateDialog = MaterialDatePicker.Builder.datePicker()
                .setSelection(calendar.timeInMillis)
                .setTitleText(getString(R.string.choose_the_date_dialog_title))
                .build()
            dateDialog.addOnPositiveButtonClickListener { timeInMillis ->
                val dateFormat = SimpleDateFormat(resources.getString(R.string.date_format))
                calendar.timeInMillis = timeInMillis
                Snackbar.make(
                    binding.calendar,
                    dateFormat.format(calendar.timeInMillis),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            dateDialog.show(childFragmentManager, "DatePicker" )
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}