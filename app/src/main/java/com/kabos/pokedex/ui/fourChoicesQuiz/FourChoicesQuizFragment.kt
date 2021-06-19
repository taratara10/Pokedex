package com.kabos.pokedex.ui.fourChoicesQuiz

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kabos.pokedex.databinding.FragmentFourChoicesMainBinding
import com.kabos.pokedex.ui.viewModel.FourChoiceViewModel

class FourChoicesQuizFragment: Fragment() {

    private lateinit var binding: FragmentFourChoicesMainBinding
    private val fourChoicesViewModel: FourChoiceViewModel by activityViewModels()
}
