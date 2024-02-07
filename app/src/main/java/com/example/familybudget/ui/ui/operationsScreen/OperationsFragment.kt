package com.example.familybudget.ui.ui.operationsScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.familybudget.databinding.FragmentOperationsBinding

class OperationsFragment : Fragment(), AddOperationBottomSheet.OnSaveClickListener {

    private val binding by lazy {
        FragmentOperationsBinding.inflate(layoutInflater)
    }
    private val viewModel: OperationsViewModel by viewModels()
    private val operationsAdapter = OperationsAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.rvOperations.adapter = operationsAdapter
        binding.btnAddOperation.setOnClickListener {
            val bottomSheetFragment = AddOperationBottomSheet()
            bottomSheetFragment.setOnSaveClickListener(this)
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }

        viewModel.operationsUIState.observe(viewLifecycleOwner) { state->
            with(binding) {
                if (state.wallet?.operations.isNullOrEmpty()) {
                    tvNotOperations.visibility = View.VISIBLE
                    operationsAdapter.submitList(null)
                } else {
                    tvNotOperations.visibility = View.GONE
                    operationsAdapter.submitList(state.wallet?.operations)
                }

                btnWalletSelection.setOnClickListener {
                    Toast.makeText(context, "btnWalletSelection clicked", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.loadOperations(0)
    }

    override fun onSaveClicked(amount: String, selectedIcon: CharSequence, place: String) {
        viewModel.onAddOperationClicked(amount, selectedIcon, place)
    }
}