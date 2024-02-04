package com.example.familybudget.ui.ui.homeScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.familybudget.databinding.FragmentHomeScreenBinding

class HomeScreenFragment : Fragment(), AddMandatoryPaymentsBottomSheet.OnSaveClickListener {

    private val binding by lazy {
        FragmentHomeScreenBinding.inflate(layoutInflater)
    }
    private val homeScreenFragmentArgs: HomeScreenFragmentArgs by navArgs()
    private val viewModel: HomeScreenViewModel by viewModels()
    private val mandatoryPaymentsAdapter = MandatoryPaymentsAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.rvMandatoryExpenses.adapter = mandatoryPaymentsAdapter
        binding.btnAddMandatoryPayments.setOnClickListener {
            val bottomSheetFragment = AddMandatoryPaymentsBottomSheet()
            bottomSheetFragment.setOnSaveClickListener(this)
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }

        viewModel.homeScreenUIState.observe(viewLifecycleOwner) { state ->
            with(binding) {
                tvCurrentIncome.text = state.wallet?.monthlyIncome.toString()
                tvCurrentExpenses.text = state.wallet?.monthlyExpenses.toString()
                tvBalanceForPeriod.text = state.balance
                tvMonth.text = state.wallet?.currentMonth
                tvRemains.text = "Остаток на ${state.wallet?.currentMonth}"
                tvRemainsAmount.text = state.balance

                if (state.wallet?.mandatoryPayments == null) {
                    tvNoMandatoryExpenses.visibility = View.VISIBLE
                } else {
                    tvNoMandatoryExpenses.visibility = View.GONE
                    mandatoryPaymentsAdapter.submitList(state.wallet!!.mandatoryPayments)
                }

                btnWalletSelection.text = state.wallet?.name
                btnWalletSelection.setOnClickListener {
                    state.wallet?.id?.let { it1 -> openWalletList(it1) }
                }

                btnActionMenu.setOnClickListener {
                    Toast.makeText(context, "Action Menu clicked", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.loadWallet(homeScreenFragmentArgs.walletId)
    }

    private fun openWalletList(currentWalletId: Int) {
        findNavController().navigate(
            HomeScreenFragmentDirections.actionHomeScreenFragmentToWalletListFragment(
                currentWalletId
            )
        )
    }

    override fun onSaveClicked(amount: String, selectedIcon: CharSequence) {
        viewModel.onMandatoryPaymentsClicked(amount, selectedIcon)
    }
}