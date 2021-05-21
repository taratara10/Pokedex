package com.kabos.pokedex.ui.pokedex

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.kabos.pokedex.R
import com.kabos.pokedex.databinding.DialogPokedexDetailBinding
import com.kabos.pokedex.ui.viewModel.PokedexViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokedexDetailFragment: DialogFragment() {

    private val pokedexViewModel: PokedexViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val binding = DataBindingUtil.inflate<DialogPokedexDetailBinding>(
            LayoutInflater.from(activity), R.layout.dialog_pokedex_detail,
            null, false)
        binding.pokemonData = pokedexViewModel.dialogPokemon

        builder.setView(binding.root)
        builder.setTitle("ポケモン図鑑")
        return builder.create()
    }

}