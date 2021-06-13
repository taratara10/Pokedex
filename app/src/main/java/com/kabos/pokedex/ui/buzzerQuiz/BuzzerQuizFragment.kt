package com.kabos.pokedex.ui.buzzerQuiz

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kabos.pokedex.R
import com.kabos.pokedex.databinding.FragmentBuzzerQuizBinding
import com.kabos.pokedex.ui.viewModel.BuzzerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuzzerQuizFragment: Fragment() {
    private lateinit var binding: FragmentBuzzerQuizBinding
    private val buzzerViewModel: BuzzerViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBuzzerQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buzzerVM = buzzerViewModel
            lifecycleOwner = this@BuzzerQuizFragment
            buzzerProgressBar.apply {
                min = 0
                max = buzzerViewModel.numberOfQuestion
            }
        }

        buzzerViewModel.goResultFragment.observe(viewLifecycleOwner,{goResult ->
            if (goResult) findNavController().navigate(R.id.action_navigation_buzzer_quiz_to_navigation_buzzer_result)
        })

        buzzerViewModel.isCollapseCardView.observe(viewLifecycleOwner, { isCollapse ->
            if (isCollapse){
                binding.apply {
                    elHintOne.collapse(false)
                    elHintTwo.collapse(false)
                    elHintThree.collapse(false)
                    elAnswer.collapse(false)
                }
            }
        })

       buzzerViewModel.currentProgress.observe(viewLifecycleOwner, { progress ->
           onProgressChanged(progress)
       })

    }

    private fun onProgressChanged(progress: Int) {
        val animation = ObjectAnimator.ofInt(binding.buzzerProgressBar, "progress", progress)
        animation.duration = 1000
        animation.interpolator = DecelerateInterpolator()
        animation.start()
        Log.d("animation", "animation called!")
    }






}