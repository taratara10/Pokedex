package com.kabos.pokedex.ui.fourChoicesQuiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
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
            inflatePieChart(pieChart)

        }
    }

    private fun inflatePieChart(pieChart: PieChart) {
        //表示用サンプルデータの作成//
        val dimensions = listOf("A", "B", "C", "D")//分割円の名称(String型)
        val values = listOf(1f, 2f, 3f, 4f)//分割円の大きさ(Float型)

        //①Entryにデータ格納
        var entryList = mutableListOf<PieEntry>()
        for(i in values.indices){
            entryList.add(
                PieEntry(values[i], dimensions[i])
            )
        }

        //②PieDataSetにデータ格納
        val pieDataSet = PieDataSet(entryList, "candle")
        //③DataSetのフォーマット指定
        pieDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()

        //④PieDataにPieDataSet格納
        val pieData = PieData(pieDataSet)
        //⑤PieChartにPieData格納
        pieChart.data = pieData
        //⑥Chartのフォーマット指定
        pieChart.legend.isEnabled = false
        //⑦PieChart更新
        pieChart.invalidate()
    }

}
