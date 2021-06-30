package com.kabos.pokedex.ui.fourChoicesQuiz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kabos.pokedex.R
import com.kabos.pokedex.databinding.FragmentFourChoicesQuizBinding
import com.kabos.pokedex.ui.viewModel.FourChoiceViewModel

class FourChoicesQuizFragment: Fragment() {

    private lateinit var binding: FragmentFourChoicesQuizBinding
    private val fourChoicesViewModel: FourChoiceViewModel by activityViewModels()

    private val backPressCallback = object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val action = FourChoicesQuizFragmentDirections
                .actionNavigationFourChoicesQuizToNavigationConfirmBackpress(fromFour = true)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFourChoicesQuizBinding.inflate(inflater, container, false)
        requireActivity().onBackPressedDispatcher.addCallback(backPressCallback)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            fourVM = fourChoicesViewModel
            lifecycleOwner = this@FourChoicesQuizFragment
            fourChoicesProgressBar.apply {
                min = 0
                max = fourChoicesViewModel.numberOfQuestion
            }
        }

        fourChoicesViewModel.isDisplayChoiceBackground.observe(viewLifecycleOwner, { background ->
            binding.apply {
                choiceOne.setBackgroundResource(background[0])
                choiceTwo.setBackgroundResource(background[1])
                choiceThree.setBackgroundResource(background[2])
                choiceFour.setBackgroundResource(background[3])
            }
        })

        fourChoicesViewModel.isCollapseCardView.observe(viewLifecycleOwner, { isCollapse ->
            if (isCollapse){
                binding.apply {
                    elHintOne.collapse(false)
                    elHintTwo.collapse(false)
                    elHintThree.collapse(false)
                    elAnswer.collapse(false)
                }
            }
        })

        //回答したら答えがオープンする
        fourChoicesViewModel.isBtnEnable.observe(viewLifecycleOwner, {isAnswered ->
            if (isAnswered) binding.elAnswer.expand()
        })

        fourChoicesViewModel.goResultFragment.observe(viewLifecycleOwner, {goResult ->
            if (goResult) findNavController().navigate(R.id.action_navigation_four_choices_quiz_to_navigation_four_choices_result)
        })


    }


}
