package com.kabos.pokedex.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.kabos.pokedex.R
import com.kabos.pokedex.databinding.DialogConfirmBackpressBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogConfirmBackPress: DialogFragment() {

    private lateinit var binding: DialogConfirmBackpressBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_confirm_backpress, null, false)


        builder.setView(binding.root)
        return builder.create()
    }
}
