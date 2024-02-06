package com.example.familybudget.ui.ui.walletList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.familybudget.databinding.FragmentWalletListBinding

class WalletListFragment : Fragment(), AddNewWalletBottomSheet.OnSaveClickListener {

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

        binding.btnNewWallet.setOnClickListener {
            val bottomSheetFragment = AddNewWalletBottomSheet()
            bottomSheetFragment.setOnSaveClickListener(this)
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }

        viewModel.walletListUIState.observe(viewLifecycleOwner) { state ->
            with(binding) {
                if (state.listWallet == null) {
                    tvNotWallet.visibility = View.VISIBLE
                } else {
                    tvNotWallet.visibility = View.GONE
                    walletListAdapter.submitList(state.listWallet)
                }

                btnWalletExit.setOnClickListener {
                    state.currentId?.let { it1 -> openWalletById(it1) }
                }
            }
        }
        setupSwipeListener(binding.rvWallets)
        viewModel.loadUser(walletListFragmentArgs.currentWalletId)
    }

    private fun setupSwipeListener(rvWalletList: RecyclerView?) {
        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    viewHolder2: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDirection: Int) {
                    val item = walletListAdapter.currentList[viewHolder.adapterPosition]
                    viewModel.deleteWallet(item.id)
                }

                override fun getSwipeDirs(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ): Int {
                    val item = walletListAdapter.currentList[viewHolder.adapterPosition]
                    return if (item.id == 0) {
                        0 // Запретить свайп для элемента с id = 0
                    } else {
                        super.getSwipeDirs(recyclerView, viewHolder)
                    }
                }
            }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvWalletList)
    }

    private fun openWalletById(walletId: Int) {
        findNavController().navigate(
            WalletListFragmentDirections.actionWalletListFragmentToHomeScreenFragment(walletId)
        )
    }

    override fun onSaveClicked(name: String) {
        viewModel.onAddNewWalletClicked(name)
    }
}