package com.example.m17_recyclerview.photomarslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.m17_recyclerview.databinding.FragmentPhotoMarsListBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PhotoMarsListFragment : Fragment() {

    private var _binding: FragmentPhotoMarsListBinding? = null

    private val binding get() = _binding!!
    private val viewModel: PhotoMarsListViewModel by viewModels()

    private val photoMarsAdapter = PhotoMarsListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoMarsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.photoList.adapter = photoMarsAdapter.withLoadStateFooter(PhotosLoadStateAdapter())

        binding.swipeRefresh.setOnRefreshListener {
            photoMarsAdapter.refresh()
        }

        photoMarsAdapter.loadStateFlow.onEach{
            binding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.photosMars.onEach {
            photoMarsAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}