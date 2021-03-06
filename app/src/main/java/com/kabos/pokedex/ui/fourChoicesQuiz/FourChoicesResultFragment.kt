package com.kabos.pokedex.ui.fourChoicesQuiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.kabos.pokedex.R
import com.kabos.pokedex.databinding.FragmentFourChoicesResultBinding
import com.kabos.pokedex.ui.viewModel.FourChoiceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FourChoicesResultFragment: Fragment() {

    private lateinit var binding: FragmentFourChoicesResultBinding
    private val fourChoiceViewModel: FourChoiceViewModel by activityViewModels()
    private val backPressCallback = object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigate(R.id.action_navigation_four_choices_result_to_navigation_four_choices_main)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().onBackPressedDispatcher.addCallback(backPressCallback)
        binding = FragmentFourChoicesResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            fourVM = fourChoiceViewModel
            btnFourResultEnd.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_four_choices_result_to_navigation_four_choices_main)
            }

            val pieChartValue = listOf(
                fourChoiceViewModel.numberOfCorrectAnswer.toFloat(),
                fourChoiceViewModel.numberOfWrongAnswer.toFloat()
            )
            pieChart.animateXY(2000, 2000)
            inflatePieChart(pieChart, pieChartValue)

        }
    }

    private fun inflatePieChart(pieChart: PieChart, values: List<Float>) {

        //???Entry??????????????????
        val entryList = mutableListOf<PieEntry>()
        for(i in values.indices){
            entryList.add(PieEntry(values[i]))
        }

        //???PieDataSet??????????????????
        val pieDataSet = PieDataSet(entryList, "candle")
        //???DataSet???????????????????????????
        pieDataSet.apply {
            colors = listOf(
                ContextCompat.getColor(requireContext(), R.color.correct),
                ContextCompat.getColor(requireContext(), R.color.wrong_transparent),
            )
            setDrawValues(false)
        }

        //???PieData???PieDataSet??????
        val pieData = PieData(pieDataSet)
        //???PieChart???PieData??????
        pieChart.data = pieData
        //???Chart???????????????????????????
        pieChart.apply {
            legend.isEnabled = false
            description.isEnabled = false
            transparentCircleRadius = 0f
            centerText ="${(values[0]/(values[0] + values[1]) *100).toInt()} %"
            setCenterTextSize(20f)
            setTouchEnabled(false)

        }

        //???PieChart??????
        pieChart.invalidate()
    }

}
