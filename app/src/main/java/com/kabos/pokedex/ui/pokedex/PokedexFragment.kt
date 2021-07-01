package com.kabos.pokedex.ui.pokedex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kabos.pokedex.R
import com.kabos.pokedex.databinding.FragmentPokedexBinding
import com.kabos.pokedex.util.InfiniteScrollListener
import com.kabos.pokedex.ui.viewModel.PokedexViewModel
import com.kabos.pokedex.util.NavigateRegionCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokedexFragment: Fragment(){

    private lateinit var binding: FragmentPokedexBinding
    private val pokedexViewModel: PokedexViewModel by activityViewModels()
    private val pokedexAdapter: PokedexAdapter = PokedexAdapter { pokemon ->
        pokedexViewModel.updateDialogPokemon(pokemon)
        if (pokedexViewModel.dialogPokemon != null) findNavController().navigate(R.id.action_navigation_pokedex_to_navigation_pokedex_detail)
    }
    private val navigateRegionCallback = object : NavigateRegionCallback {
        override fun navigateRegionFragment() {
            val action = PokedexFragmentDirections.actionNavigationPokedexToNavigationRegionSelect(fromPokedex = true)
            findNavController().navigate(action)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.title = "ポケモンずかん"
        binding = FragmentPokedexBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setupRecyclerView()
            pokedexVM = pokedexViewModel
            lifecycleOwner = this@PokedexFragment
            callback = navigateRegionCallback
        }

        pokedexViewModel.pokemonList.observe(viewLifecycleOwner, {list ->
            //DiffUtil hasn't store old data, so it cannot compare and update UI. we should store old data instance.
            val newList = list.toList()
            pokedexAdapter.submitList(newList)
        })
    }

    private fun setupRecyclerView() {
        binding.rvPokedex.apply {
            val layout = LinearLayoutManager(activity)
            adapter = pokedexAdapter
            layoutManager = layout
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener(layout) {
                pokedexViewModel.getPokemonList()
            })
        }
    }



}


