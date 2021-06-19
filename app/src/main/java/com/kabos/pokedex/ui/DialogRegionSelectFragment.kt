package com.kabos.pokedex.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
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
import com.kabos.pokedex.ui.fourChoicesQuiz.FourChoicesMainFragmentArgs
import com.kabos.pokedex.util.RegionCallback
import com.kabos.pokedex.ui.pokedex.PokedexFragmentArgs
import com.kabos.pokedex.ui.viewModel.BuzzerViewModel
import com.kabos.pokedex.ui.viewModel.FourChoiceViewModel
import com.kabos.pokedex.ui.viewModel.PokedexViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogRegionSelectFragment: DialogFragment() {

    private lateinit var binding: DialogRegionSelectBinding

    val pokedexViewModel: PokedexViewModel by activityViewModels()
    val buzzerViewModel: BuzzerViewModel by activityViewModels()
    val fourChoiceViewModel: FourChoiceViewModel by activityViewModels()
    private val pokedexFragmentArgs: PokedexFragmentArgs by navArgs()
    private val buzzerMainFragmentArgs: BuzzerMainFragmentArgs by navArgs()
    private val fourchoicesFragmentArgs: FourChoicesMainFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_region_select, null, false)

        binding.callback = object : RegionCallback {
            override fun onClick(region: Region) {
                //check navigation
                if (pokedexFragmentArgs.fromPokedex) {
                    pokedexViewModel.updateRegion(region)
                    findNavController().popBackStack()
                }
                if (buzzerMainFragmentArgs.fromBuzzer) {
                    buzzerViewModel.currentRegion.postValue(region)
                    findNavController().popBackStack()
                }
                if (fourchoicesFragmentArgs.fromFour) {
                    fourChoiceViewModel.currentRegion.postValue(region)
                    findNavController().popBackStack()
                }
            }
        }

        builder.setView(binding.root)
        return builder.create()
    }




}
