package com.kabos.pokedex.ui.fourChoicesQuiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFourChoicesResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            fourVM = fourChoiceViewModel

            val pieChartValue = listOf(
                fourChoiceViewModel.numberOfCorrectAnswer.toFloat(),
                fourChoiceViewModel.numberOfWrongAnswer.toFloat()
            )
            pieChart.animateXY(2000, 2000)
            inflatePieChart(pieChart, pieChartValue)

        }
    }

    private fun inflatePieChart(pieChart: PieChart, values: List<Float>) {

        //①Entryにデータ格納
        val entryList = mutableListOf<PieEntry>()
        for(i in values.indices){
            entryList.add(PieEntry(values[i]))
        }

        //②PieDataSetにデータ格納
        val pieDataSet = PieDataSet(entryList, "candle")
        //③DataSetのフォーマット指定
        pieDataSet.apply {
            colors = listOf(
                ContextCompat.getColor(requireContext(), R.color.correct),
                ContextCompat.getColor(requireContext(), R.color.wrong_transparent),
            )

            setDrawValues(false)
        }

        //④PieDataにPieDataSet格納
        val pieData = PieData(pieDataSet)
        //⑤PieChartにPieData格納
        pieChart.data = pieData
        //⑥Chartのフォーマット指定
        pieChart.apply {
            legend.isEnabled = false
            description.isEnabled = false
            transparentCircleRadius = 0f
            centerText ="${(values[0]/(values[0] + values[1]) *100).toInt()} %"
            setCenterTextSize(20f)
            setTouchEnabled(false)

        }

        //⑦PieChart更新
        pieChart.invalidate()
    }

}
