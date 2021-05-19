package com.kabos.pokedex.ui.pokedex

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.kabos.pokedex.R
import com.kabos.pokedex.databinding.DialogPokedexDetailBinding

class PokedexDetailFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val binding = DataBindingUtil.inflate<DialogPokedexDetailBinding>(
            LayoutInflater.from(activity), R.layout.dialog_pokedex_detail,
            null, false)

        builder.setView(binding.root)
        return builder.create()
    }

}