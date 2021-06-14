package com.kabos.pokedex.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kabos.pokedex.R
import com.kabos.pokedex.databinding.DialogRegionSelectBinding
import com.kabos.pokedex.model.Region
import com.kabos.pokedex.ui.buzzerQuiz.BuzzerMainFragmentArgs
import com.kabos.pokedex.util.RegionCallback
import com.kabos.pokedex.ui.pokedex.PokedexFragmentArgs
import com.kabos.pokedex.ui.viewModel.BuzzerViewModel
import com.kabos.pokedex.ui.viewModel.PokedexViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogRegionSelectFragment: DialogFragment() {

    private lateinit var binding: DialogRegionSelectBinding

    val pokedexViewModel: PokedexViewModel by activityViewModels()
    val buzzerViewModel: BuzzerViewModel by activityViewModels()
    private val pokedexFragmentArgs: PokedexFragmentArgs by navArgs()
    private val buzzerMainFragmentArgs: BuzzerMainFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(activity),
                R.layout.dialog_region_select,
                null, false)

        binding.callback = object : RegionCallback {
            override fun onClick(region: Region) {
                //check navigation
                //todo 毎回isBackStackってリセットされるのか？　よしなに初期化処理書く
                if (pokedexFragmentArgs.isBackStack) {
                    pokedexViewModel.updateRegion(region)
                    findNavController().popBackStack()
                }
                //todo  Add buzzer and choices navigation
                //isBackStack をfalseでリセットしないと、Fragment増えた時にバグりそう
                if (buzzerMainFragmentArgs.isBackStack) {
                    buzzerViewModel.currentRegion.postValue(region)
                    findNavController().popBackStack()
                }
            }
        }

        builder.setView(binding.root)
        return builder.create()
    }




}