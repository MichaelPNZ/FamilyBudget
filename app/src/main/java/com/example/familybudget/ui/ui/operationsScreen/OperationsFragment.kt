package com.example.familybudget.ui.ui.operationsScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.familybudget.databinding.FragmentOperationsBinding
import com.google.android.material.tabs.TabLayout

class OperationsFragment : Fragment(), AddOperationBottomSheet.OnSaveClickListener {

    private val binding by lazy {
        FragmentOperationsBinding.inflate(layoutInflater)
    }
    private val operationsFragmentArgs: OperationsFragmentArgs by navArgs()
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

        viewModel.operationsUIState.observe(viewLifecycleOwner) { state ->
            with(binding) {
                if (state.wallet?.operations?.isEmpty() == true) {
                    tvNotOperations.visibility = View.VISIBLE
                    rvOperations.visibility = View.GONE
                } else {
                    tvNotOperations.visibility = View.GONE
                    rvOperations.visibility = View.VISIBLE

                    tlOperationsScreen.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                        override fun onTabSelected(tab: TabLayout.Tab?) {
                            when (tab?.position) {
                                0 -> {
                                    viewModel.onDayChanged(
                                        operationsFragmentArgs.walletId,
                                        0
                                    )
                                }

                                1 -> {
                                    viewModel.onDayChanged(
                                        operationsFragmentArgs.walletId,
                                        1
                                    )
                                }

                                2 -> {
                                    viewModel.onDayChanged(
                                        operationsFragmentArgs.walletId,
                                        2
                                    )
                                }

                                3 -> {
                                    viewModel.onDayChanged(
                                        operationsFragmentArgs.walletId,
                                        3
                                    )
                                }

                            }
                        }

                        override fun onTabUnselected(tab: TabLayout.Tab?) {}
                        override fun onTabReselected(tab: TabLayout.Tab?) {}
                    })
                    operationsAdapter.submitList(state.wallet?.operations)
                }
                btnWalletSelectionFromOperations.text = state.wallet?.name
                btnWalletSelectionFromOperations.setOnClickListener {
                    state.wallet?.id?.let { it1 -> openWalletList(it1) }
                }
            }
        }
        viewModel.loadOperations(operationsFragmentArgs.walletId)
    }

    private fun openWalletList(currentWalletId: Int) {
        findNavController().navigate(
            OperationsFragmentDirections.actionOperationsFragmentToWalletListFragment(
                currentWalletId
            )
        )
    }

    override fun onSaveClicked(
        amount: String,
        selectedIcon: CharSequence,
        place: String,
        date: String
    ) {
        viewModel.onAddOperationClicked(amount, selectedIcon, place, date)
    }
}