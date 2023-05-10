package com.example.m20_firebase


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.m20_firebase.databinding.GalleryFragmentBinding
import kotlinx.coroutines.launch

class GalleryFragment : Fragment() {

    private var _binding: GalleryFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var gridAdapter: GridAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = GalleryFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = PhotoRepository(requireContext().applicationContext)

        viewLifecycleOwner.lifecycleScope.launch {
            val photos = repository.getAllPhotos()
            binding.recyclerView.adapter = GridAdapter(requireContext(), photos)
        }
    }
}
