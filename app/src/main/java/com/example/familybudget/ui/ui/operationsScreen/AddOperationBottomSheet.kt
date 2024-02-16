package com.example.familybudget.ui.ui.operationsScreen

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.familybudget.R
import com.example.familybudget.databinding.AddOperationBottomSheetLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddOperationBottomSheet : BottomSheetDialogFragment() {

    private val binding by lazy {
        AddOperationBottomSheetLayoutBinding.inflate(layoutInflater)
    }

    private lateinit var onSaveClickListener: OnSaveClickListener
    private val calendar: Calendar = Calendar.getInstance()

    fun setOnSaveClickListener(listener: OnSaveClickListener) {
        this.onSaveClickListener = listener
    }

    interface OnSaveClickListener {
        fun onSaveClicked(
            amount: String,
            selectedIcon: CharSequence,
            place: String,
            date: String
        )
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

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            etDateOperationScreen.setOnClickListener {
                showDatePickerDialog(year, month, day)
            }

            btnSaveOperationScreen.setOnClickListener {
                val amountStr = etAmountOperationScreen.text.toString()
                val place = etNameOfPlaceOperationScreen.text.toString()
                val amount = if (amountStr.isNotEmpty()) amountStr.toInt() else 0
                val isIncome = rbIncome.isChecked

                onSaveClickListener.onSaveClicked(
                    if (!isIncome) (-amount).toString() else amount.toString(),
                    spinnerOperationScreen.selectedItem.toString(),
                    place,
                    etDateOperationScreen.text.toString()
                )
                dismiss()
            }
        }
        return binding.root
    }

    private fun showDatePickerDialog(year: Int, month: Int, day: Int) {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val dateString = SimpleDateFormat("dd MMMM yyyy", Locale("ru")).format(calendar.time)
                binding.etDateOperationScreen.setText(dateString)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
}