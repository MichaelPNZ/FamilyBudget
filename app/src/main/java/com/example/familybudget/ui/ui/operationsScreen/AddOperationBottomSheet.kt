package com.example.familybudget.ui.ui.operationsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.familybudget.R
import com.example.familybudget.databinding.AddOperationBottomSheetLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddOperationBottomSheet : BottomSheetDialogFragment() {

    private val binding by lazy {
        AddOperationBottomSheetLayoutBinding.inflate(layoutInflater)
    }

    private lateinit var onSaveClickListener: OnSaveClickListener

    fun setOnSaveClickListener(listener: OnSaveClickListener) {
        this.onSaveClickListener = listener
    }

    interface OnSaveClickListener {
        fun onSaveClicked(amount: String, selectedIcon: CharSequence, place: String)
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
            spinnerOperationScreen.adapter = adapter
            btnSaveOperationScreen.setOnClickListener {
                val amount = etAmountOperationScreen.text.toString()
                val place = etNameOfPlaceOperationScreen.text.toString()
                if (amount == "" || place == "") {
                    val toast = Toast.makeText(
                        context,
                        "Ошибка! Вы не ввели сумму платежа или название места",
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                } else {
                    onSaveClickListener.onSaveClicked(
                        amount,
                        spinnerOperationScreen.selectedItem.toString(),
                        place
                    )
                }
                dismiss()
            }
        }
        return binding.root
    }
}