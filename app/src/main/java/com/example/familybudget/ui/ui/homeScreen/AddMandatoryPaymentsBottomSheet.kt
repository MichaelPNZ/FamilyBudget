package com.example.familybudget.ui.ui.homeScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.familybudget.R
import com.example.familybudget.databinding.BottomSheetLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddMandatoryPaymentsBottomSheet : BottomSheetDialogFragment() {

    private val binding by lazy {
        BottomSheetLayoutBinding.inflate(layoutInflater)
    }
    private lateinit var onSaveClickListener: OnSaveClickListener

    fun setOnSaveClickListener(listener: OnSaveClickListener) {
        this.onSaveClickListener = listener
    }

    interface OnSaveClickListener {
        fun onSaveClicked(amount: String, selectedIcon: CharSequence)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.icon_array,
                android.R.layout.simple_spinner_item
            )
        }
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
        binding.btnSave.setOnClickListener {
            val amount = binding.etAmount.text.toString()
            if (amount == "") {
                val toast = Toast.makeText(
                    context,
                    "Ошибка! Вы не ввели сумму платежа",
                    Toast.LENGTH_LONG
                )
                toast.show()
            } else {
                onSaveClickListener.onSaveClicked(
                    amount,
                    binding.spinner.selectedItem.toString()
                )
            }
            dismiss()
        }
        return binding.root
    }
}
