package com.kabos.pokedex.ui.buzzerQuiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kabos.pokedex.databinding.FragmentBuzzerMainBinding
import com.kabos.pokedex.ui.viewModel.BuzzerViewModel

class BuzzerMainFragment: Fragment() {

    private lateinit var binding: FragmentBuzzerMainBinding
    private val buzzerViewModel: BuzzerViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBuzzerMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}