package com.kabos.pokedex.ui.buzzerQuiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kabos.pokedex.databinding.FragmentBuzzerMainBinding
import com.kabos.pokedex.ui.viewModel.BuzzerViewModel
import com.kabos.pokedex.util.BuzzerQuizCallback

class BuzzerMainFragment: Fragment() {

    private lateinit var binding: FragmentBuzzerMainBinding
    private val buzzerViewModel: BuzzerViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBuzzerMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            buzzerVM = buzzerViewModel
            callback = object: BuzzerQuizCallback {
                override fun navigateRegionFragment() {
                    val action = BuzzerMainFragmentDirections
                            .actionNavigationBuzzerMainToNavigationRegionSelect(isBackStack = true)
                    findNavController().navigate(action)
                }

                override fun updateQuestionsNumber() {
                    buzzerViewModel.updateQuestionsNumber()
                }

                override fun updatePlayersNumber() {
                    TODO("Not yet implemented")
                }

            }
            btnStart.setOnClickListener { buzzerViewModel.updateQuestionsNumber() }
        }
    }
}