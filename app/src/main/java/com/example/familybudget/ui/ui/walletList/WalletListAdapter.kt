package com.example.familybudget.ui.ui.walletList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.familybudget.R
import com.example.familybudget.ui.model.Wallet

class WalletListAdapter : ListAdapter<Wallet, WalletListAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Wallet>() {
        override fun areItemsTheSame(oldItem: Wallet, newItem: Wallet): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Wallet, newItem: Wallet): Boolean {
            return oldItem == newItem
        }
    }
) {

    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(walletId: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvWallets: CardView = view.findViewById(R.id.cvWallet)
        val tvWalletName: TextView = view.findViewById(R.id.tvWalletName)
        val tvWalletBalance: TextView = view.findViewById(R.id.tvWalletBalance)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_wallet, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        with(viewHolder) {
            tvWalletName.text = currentItem.name
            tvWalletBalance.text =
                (currentItem.monthlyIncome - currentItem.monthlyExpenses).toString()
            cvWallets.setOnClickListener {
                itemClickListener?.onItemClick(currentItem.id)
            }
        }
    }
}