package com.kabos.pokedex.ui.fourChoicesQuiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kabos.pokedex.R
import com.kabos.pokedex.databinding.FragmentFourChoicesMainBinding
import com.kabos.pokedex.ui.viewModel.FourChoiceViewModel
import com.kabos.pokedex.util.NavigateRegionCallback

class FourChoicesMainFragment: Fragment() {

    private lateinit var binding: FragmentFourChoicesMainBinding
    private val fourChoicesViewModel: FourChoiceViewModel by activityViewModels()
    private val navigateRegionCallback = object: NavigateRegionCallback {
        override fun navigateRegionFragment() {
            val action = FourChoicesMainFragmentDirections
                .actionNavigationFourChoicesToNavigationRegionSelect(fromFour = true)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFourChoicesMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            fourVM = fourChoicesViewModel
            callback = navigateRegionCallback
            lifecycleOwner = this@FourChoicesMainFragment

            btnFourChoicesStart.setOnClickListener {
                fourChoicesViewModel.startQuestion()
                findNavController().navigate(R.id.action_navigation_four_choices_main_to_navigation_four_choices_quiz)
            }

        }
    }
}
