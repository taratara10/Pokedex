package com.kabos.pokedex.ui.buzzerQuiz

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayoutStates
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.kabos.pokedex.R
import com.kabos.pokedex.databinding.FragmentBuzzerResultBinding
import com.kabos.pokedex.ui.viewModel.BuzzerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuzzerResultFragment: Fragment() {

    private lateinit var binding: FragmentBuzzerResultBinding
    private val buzzerViewModel: BuzzerViewModel by activityViewModels()
    private val backPressCallback = object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigate(R.id.action_navigation_buzzer_result_to_navigation_buzzer_main)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().onBackPressedDispatcher.addCallback(backPressCallback)
        binding = FragmentBuzzerResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            buzzerVM = buzzerViewModel
            btnBuzzerEnd.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_buzzer_result_to_navigation_buzzer_main)
            }

            //calculate barChart height
            barChart.layoutParams.height = ((buzzerViewModel.numberOfPlayer * 50 + 80) * resources.displayMetrics.density).toInt()
            barChart.animateY(2000)
            val xAxisPlayerList = mutableListOf(1f, 2f, 3f, 4f, 5f, 6f, 7f).take(buzzerViewModel.numberOfPlayer + 1)
            inflateHorizontalBarChart(barChart, xAxisPlayerList, buzzerViewModel.playerScoreList)
        }
    }

    private fun inflateHorizontalBarChart(barChart: BarChart, xData: List<Float>, yData: List<Float>) {
        //Entry:?????????1?????????????????????
        //DataSet:????????????????????????????????????
        //Data:????????????????????????????????????
        //Chart: Data + UI

        //???Entry??????????????????
        val entryList = mutableListOf<BarEntry>()
        for(i in xData.indices) {
            entryList.add(
                    BarEntry(xData[xData.size - 1 - i], yData[i])
            )
        }
        //BarDataSet????????????
        val barDataSets = mutableListOf<IBarDataSet>()
        //???DataSet??????????????????
        val barDataSet = BarDataSet(entryList,"????????????")
        //???DataSet???????????????????????????
        barDataSet.apply {
            color = Color.BLUE
            valueTextSize = 18f
            valueFormatter =  object: ValueFormatter(){
                override fun getFormattedValue(value: Float): String{
                    return value.toInt().toString()
                }
            }
        }
        //??????????????????
        barDataSets.add(barDataSet)

        //???BarData???BarDataSet??????
        val barData = BarData(barDataSets)
        barData.barWidth = .5f
        //???BarChart???BarData??????
        barChart.data = barData
        //???Chart???????????????????????????
        barChart.apply {
            xAxis.isEnabled = false
            axisRight.isEnabled = false
            axisLeft.isEnabled = false
            axisLeft.axisMinimum = 0f
            axisLeft.textSize = 18f
            description.isEnabled = false
            legend.isEnabled = false
            setScaleEnabled(false)
            setTouchEnabled(false)
            setPinchZoom(false)
        }
        //???barChart??????
        barChart.invalidate()

    }

}
