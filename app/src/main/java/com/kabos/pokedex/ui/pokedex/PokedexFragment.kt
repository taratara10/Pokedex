package com.kabos.pokedex.ui.pokedex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.kabos.pokedex.databinding.FragmentPokedexBinding
import com.kabos.pokedex.ui.viewModel.PokedexViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokedexFragment: Fragment(){

    private lateinit var binding: FragmentPokedexBinding
    private val pokedexViewModel: PokedexViewModel by viewModels()
    private val pokedexAdapter: PokedexAdapter = PokedexAdapter { pokemon ->
//        val action = PokedexFragmentDirection
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPokedexBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokedexViewModel.pokemonListOne.observe(viewLifecycleOwner, {list ->
            pokedexAdapter.submitList(list)
        })

        binding.apply {
            rvPokedex.adapter = pokedexAdapter
            lifecycleOwner = this@PokedexFragment
        }

    }
}