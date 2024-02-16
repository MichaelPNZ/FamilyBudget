package com.example.familybudget.ui.ui.homeScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.familybudget.R
import com.example.familybudget.databinding.AddMondatoryPaymentsBottomSheetLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddMandatoryPaymentsBottomSheet : BottomSheetDialogFragment() {

    private val binding by lazy {
        AddMondatoryPaymentsBottomSheetLayoutBinding.inflate(layoutInflater)
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
        with(binding) {
            spinner.adapter = adapter
            btnSave.setOnClickListener {
                val amount = etAmount.text.toString()
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
                        spinner.selectedItem.toString()
                    )
                }
                dismiss()
            }
        }
        return binding.root
    }
}
