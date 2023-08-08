package com.example.jetpack_compose.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.jetpack_compose.adapters.CharacterListAdapter
import com.example.jetpack_compose.adapters.CharactersLoadStateAdapter
import com.example.jetpack_compose.R
import com.example.jetpack_compose.databinding.FragmentCharactersBinding
import com.example.jetpack_compose.viewmodels.CharacterListViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CharacterListFragment : Fragment(), CharacterListAdapter.OnItemClickListener {

    private var _binding: FragmentCharactersBinding? = null

    private val binding get() = _binding!!
    private val viewModel: CharacterListViewModel by viewModels()

    private lateinit var characterListAdapter: CharacterListAdapter

    override fun onItemClick(position: Int) {
        val bundle = Bundle().apply {
            putInt("character_id", position+1)
        }
        val fragment = CharacterInfoFragment()
        fragment.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        characterListAdapter = CharacterListAdapter(requireContext(), this)

        binding.characters.adapter =
            characterListAdapter.withLoadStateFooter(CharactersLoadStateAdapter())

        binding.swipeRefresh.setOnRefreshListener {
            characterListAdapter.refresh()
        }

        characterListAdapter.loadStateFlow.onEach {
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
