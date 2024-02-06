package com.example.familybudget.ui.ui.walletList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.familybudget.databinding.AddNewWalletBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddNewWalletBottomSheet: BottomSheetDialogFragment() {

    private val binding by lazy {
        AddNewWalletBottomSheetBinding.inflate(layoutInflater)
    }
    private lateinit var onSaveClickListener: OnSaveClickListener

    fun setOnSaveClickListener(listener: OnSaveClickListener) {
        this.onSaveClickListener = listener
    }

    interface OnSaveClickListener {
        fun onSaveClicked(name: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.btnSaveWallet.setOnClickListener {
            val name = binding.etNewWalletName.text.toString()
            if (name == "") {
                val toast = Toast.makeText(
                    context,
                    "Ошибка! Вы не ввели название",
                    Toast.LENGTH_LONG
                )
                toast.show()
            } else {
                onSaveClickListener.onSaveClicked(name)
            }
            dismiss()
        }
        return binding.root
    }
}