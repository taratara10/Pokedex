package com.kabos.pokedex.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.kabos.pokedex.R
import com.kabos.pokedex.databinding.DialogRegionSelectBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogRegionSelectFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val binding = DataBindingUtil.inflate<DialogRegionSelectBinding>(
                LayoutInflater.from(activity), R.layout.dialog_region_select,
                null, false)
        builder.setView(binding.root)
        return builder.create()
    }
}