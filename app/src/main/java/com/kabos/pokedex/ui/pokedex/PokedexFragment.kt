package com.kabos.pokedex.ui.pokedex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kabos.pokedex.R
import com.kabos.pokedex.databinding.FragmentPokedexBinding
import com.kabos.pokedex.ui.viewModel.PokedexViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokedexFragment: Fragment(){

    private lateinit var binding: FragmentPokedexBinding
    private val pokedexViewModel: PokedexViewModel by activityViewModels()
    private val pokedexAdapter: PokedexAdapter = PokedexAdapter { pokemon ->
        pokedexViewModel.updateDialogPokemon(pokemon)
        if (pokedexViewModel.dialogPokemon != null) findNavController().navigate(R.id.action_navigation_pokedex_to_navigation_pokedex_detail)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPokedexBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokedexViewModel.pokemonList.observe(viewLifecycleOwner, {list ->
            pokedexAdapter.submitList(list)
        })

        pokedexViewModel.currentRegion.observe(viewLifecycleOwner, {
            //todo region変更時にリストを更新する処理
        })

        binding.apply {
            rvPokedex.adapter = pokedexAdapter
            lifecycleOwner = this@PokedexFragment
        }

    }
}