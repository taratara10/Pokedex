package com.kabos.pokedex.ui.fourChoicesQuiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kabos.pokedex.databinding.FragmentFourChoicesQuizBinding
import com.kabos.pokedex.ui.viewModel.FourChoiceViewModel

class FourChoicesQuizFragment: Fragment() {

    private lateinit var binding: FragmentFourChoicesQuizBinding
    private val fourChoicesViewModel: FourChoiceViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFourChoicesQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            fourVM = fourChoicesViewModel
            lifecycleOwner = this@FourChoicesQuizFragment
        }
    }
}
