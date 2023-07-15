package com.example.recyclerview_advanced

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.recyclerview_advanced.databinding.FragmentCharactersBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null

    private val binding get() = _binding!!
    private val viewModel: CharactersViewModel by viewModels()

    private val characterListAdapter = CharacterListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.characters.adapter = characterListAdapter.withLoadStateFooter(CharactersLoadStateAdapter())

        characterListAdapter.loadStateFlow.onEach{
            binding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.characters.onEach {
            characterListAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
