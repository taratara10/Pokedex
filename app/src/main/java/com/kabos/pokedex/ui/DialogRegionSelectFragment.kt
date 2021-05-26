package com.kabos.pokedex.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kabos.pokedex.R
import com.kabos.pokedex.databinding.DialogRegionSelectBinding
import com.kabos.pokedex.model.Region
import com.kabos.pokedex.ui.callback.RegionCallback
import com.kabos.pokedex.ui.pokedex.PokedexFragmentArgs
import com.kabos.pokedex.ui.viewModel.PokedexViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogRegionSelectFragment: DialogFragment() {

    private lateinit var binding: DialogRegionSelectBinding

    val pokedexViewModel: PokedexViewModel by activityViewModels()
    private val pokedexFragmentArgs: PokedexFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(activity),
                R.layout.dialog_region_select,
                null, false)

        binding.callback = object :RegionCallback {
            override fun onClick(region: Region) {
                //check navigation
                //todo 毎回isBackStackってリセットされるのか？　よしなに初期化処理書く
                if (pokedexFragmentArgs.isBackStack) {
                    pokedexViewModel.updateRegion(region)
                    findNavController().popBackStack()
                }
                //todo  Add buzzer and choices navigation
                //isBackStack をfalseでリセットしないと、Fragment増えた時にバグりそう

            }
        }

        builder.setView(binding.root)
        return builder.create()
    }




}