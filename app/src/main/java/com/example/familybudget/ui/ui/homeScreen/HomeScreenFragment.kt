package com.example.familybudget.ui.ui.homeScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.familybudget.databinding.FragmentHomeScreenBinding

class HomeScreenFragment : Fragment() {

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
        viewModel.homeScreenUIState.observe(viewLifecycleOwner) { state ->
            with(binding) {
                tvCurrentMonth.text = state.wallet?.currentMonth
                tvCurrentIncome.text = state.wallet?.monthlyIncome.toString()
                tvCurrentExpenses.text = state.wallet?.monthlyExpenses.toString()
                tvBalanceForPeriod.text = state.balance
                tvRemains.text = state.balance

                mandatoryPaymentsAdapter.dataSet = state.wallet?.mandatoryPayments ?: emptyList()
                rvMandatoryExpenses.adapter = mandatoryPaymentsAdapter
            }
        }
        viewModel.loadWallet(0)
    }

//    private fun openMandatoryPaymentById(mandatoryPaymentsId: Int) {
//        findNavController().navigate()
//    }
}