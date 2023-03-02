package com.example.retrofit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.retrofit.R
import com.example.retrofit.databinding.FragmentLoadingBinding
import com.example.retrofit.databinding.FragmentMainBinding

class LoadingFragment : Fragment(R.layout.fragment_loading) {

    private var _binding: FragmentLoadingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoadingBinding.inflate(inflater)
        return binding.root
    }
}
