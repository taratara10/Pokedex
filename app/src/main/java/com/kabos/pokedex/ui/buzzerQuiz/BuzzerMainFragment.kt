package com.kabos.pokedex.ui.buzzerQuiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kabos.pokedex.R
import com.kabos.pokedex.databinding.FragmentBuzzerMainBinding
import com.kabos.pokedex.ui.viewModel.BuzzerViewModel
import com.kabos.pokedex.util.NavigateRegionCallback

class BuzzerMainFragment: Fragment() {

    private lateinit var binding: FragmentBuzzerMainBinding
    private val buzzerViewModel: BuzzerViewModel by activityViewModels()

    private val navigateRegionCallback = object: NavigateRegionCallback {
        override fun navigateRegionFragment() {
            val action = BuzzerMainFragmentDirections
                    .actionNavigationBuzzerMainToNavigationRegionSelect(isBackStack = true)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBuzzerMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            buzzerVM = buzzerViewModel
            callback = navigateRegionCallback
            setupNumberPicker(npBuzzerPlayer)
            btnBuzzerStart.setOnClickListener { findNavController().navigate(R.id.action_navigation_buzzer_main_to_navigation_buzzer_quiz) }
        }
    }

    private fun setupNumberPicker(numberPicker: NumberPicker) {
        numberPicker.apply {
            minValue = 2
            maxValue = 6
            wrapSelectorWheel = false
            setOnValueChangedListener { _, _, newVal ->
                buzzerViewModel.numberOfPlayer = newVal
                binding.apply {
                    ivPlayerBall3.visibility = buzzerViewModel.isDisplayPlayerImage(3)
                    ivPlayerBall4.visibility = buzzerViewModel.isDisplayPlayerImage(4)
                    ivPlayerBall5.visibility = buzzerViewModel.isDisplayPlayerImage(5)
                    ivPlayerBall6.visibility = buzzerViewModel.isDisplayPlayerImage(6)
                }
            }
        }
    }


}