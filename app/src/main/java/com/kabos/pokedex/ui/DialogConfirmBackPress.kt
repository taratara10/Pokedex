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
import com.kabos.pokedex.databinding.DialogConfirmBackpressBinding
import com.kabos.pokedex.ui.buzzerQuiz.BuzzerMainFragmentArgs
import com.kabos.pokedex.ui.fourChoicesQuiz.FourChoicesMainFragmentArgs
import com.kabos.pokedex.ui.viewModel.BuzzerViewModel
import com.kabos.pokedex.ui.viewModel.FourChoiceViewModel
import com.kabos.pokedex.util.ConfirmBackPressCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogConfirmBackPress: DialogFragment() {

    private lateinit var binding: DialogConfirmBackpressBinding
    private val buzzerViewModel: BuzzerViewModel by activityViewModels()
    private val fourChoiceViewModel: FourChoiceViewModel by activityViewModels()
    private val buzzerFragmentArgs: BuzzerMainFragmentArgs by navArgs()
    private val fourChoicesFragmentArgs: FourChoicesMainFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_confirm_backpress, null, false)

        binding.callback = object: ConfirmBackPressCallback {
            override fun navigateMainFragment() {
                if (buzzerFragmentArgs.fromBuzzer) {
                    findNavController().navigate(R.id.action_navigation_confirm_backpress_to_navigation_buzzer_main)
                }
                if (fourChoicesFragmentArgs.fromFour) {
                    findNavController().navigate(R.id.action_navigation_confirm_backpress_to_navigation_four_choices_main)
                }
            }

            override fun navigateResultFragment() {
                //一旦QuizFragmentにpopBackしてからじゃないと、destinationが異なるので移動できない
                if (buzzerFragmentArgs.fromBuzzer) {
                    findNavController().popBackStack()
                    buzzerViewModel.navigateResultFragment()
                }
                if (fourChoicesFragmentArgs.fromFour) {
                    findNavController().popBackStack()
                    fourChoiceViewModel.navigateResultFragment()
                }
            }
        }
        builder.setView(binding.root)
        return builder.create()
    }
}
