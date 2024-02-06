package com.example.familybudget.ui.ui.homeScreen

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.familybudget.R
import com.example.familybudget.ui.model.MandatoryPayment
import java.io.InputStream

class MandatoryPaymentsAdapter(private val fragment: Fragment? = null) :
    ListAdapter<MandatoryPayment, MandatoryPaymentsAdapter.ViewHolder>(
        object : DiffUtil.ItemCallback<MandatoryPayment>() {
            override fun areItemsTheSame(
                oldItem: MandatoryPayment,
                newItem: MandatoryPayment
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MandatoryPayment,
                newItem: MandatoryPayment
            ): Boolean {
                return oldItem == newItem
            }
        }
    ) {

    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(categoryId: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvMandatoryExpenses: CardView = view.findViewById(R.id.cvMandatoryExpenses)
        val ivMandatoryPayments: ImageView = view.findViewById(R.id.ivMandatoryPayments)
        val tvMandatoryPayments: TextView = view.findViewById(R.id.tvMandatoryPayments)
        val tvMandatoryPaymentsAmount: TextView = view.findViewById(R.id.tvMandatoryPaymentsAmount)
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_mandatory_expenses, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        try {
            val inputStream: InputStream? = fragment?.context?.assets?.open(currentItem.imageUrl)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            viewHolder.ivMandatoryPayments.setImageBitmap(bitmap)
        } catch (ex: Exception) {
            Log.e("mylog", "Error: ${ex.stackTraceToString()}")
        }

        with(viewHolder) {
            tvMandatoryPayments.text = currentItem.nameOfPayment
            tvMandatoryPaymentsAmount.text = currentItem.amountOfPayment.toString()
            checkBox.isChecked = currentItem.isDone
            cvMandatoryExpenses.setOnClickListener {
                itemClickListener?.onItemClick(currentItem.id)
            }
        }
    }
}