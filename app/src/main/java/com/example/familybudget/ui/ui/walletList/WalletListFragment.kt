package com.example.familybudget.ui.ui.walletList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.familybudget.databinding.FragmentWalletListBinding

class WalletListFragment : Fragment() {

    private val binding by lazy {
        FragmentWalletListBinding.inflate(layoutInflater)
    }
    private val walletListFragmentArgs: WalletListFragmentArgs by navArgs()
    private val viewModel: WalletListViewModel by viewModels()
    private val walletListAdapter = WalletListAdapter()

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
        binding.rvWallets.adapter = walletListAdapter
        walletListAdapter.setOnItemClickListener(object : WalletListAdapter.OnItemClickListener {
            override fun onItemClick(walletId: Int) {
                openWalletById(walletId)
            }
        })

        viewModel.walletListUIState.observe(viewLifecycleOwner) { state ->
            with(binding) {

                if (state.listWallet == null) {
                    tvNotWallet.visibility = View.VISIBLE
                } else {
                    tvNotWallet.visibility = View.GONE
                    walletListAdapter.submitList(state.listWallet.reversed())
                }

                btnWalletExit.setOnClickListener {
                    state.currentId?.let { it1 -> openWalletById(it1) }
                }

                btnActionMenu.setOnClickListener {

                }
            }
        }
        viewModel.loadUser(walletListFragmentArgs.currentWalletId)
    }

    private fun openWalletById(walletId: Int) {
        findNavController().navigate(
            WalletListFragmentDirections.actionWalletListFragmentToHomeScreenFragment(walletId)
        )
    }

}