package com.kabos.pokedex.util

import com.kabos.pokedex.model.Region
import com.kabos.pokedex.ui.viewModel.PokedexViewModel
import dagger.hilt.android.AndroidEntryPoint

//DialogRegionSelectで実装
interface RegionCallback {
    fun onClick(region: Region)
}

interface NavigateRegionCallback {
    fun navigateRegionFragment()
}

interface ConfirmBackPressCallback {
    fun navigateMainFragment()
    fun navigateResultFragment()
}
