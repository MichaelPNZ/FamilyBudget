package com.example.familybudget.ui.ui.operationsScreen

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.familybudget.R
import com.example.familybudget.ui.model.Operation
import java.io.InputStream

class OperationsAdapter(private val fragment: Fragment? = null) :
    ListAdapter<Operation, RecyclerView.ViewHolder>(
        object : DiffUtil.ItemCallback<Operation>() {
            override fun areItemsTheSame(oldItem: Operation, newItem: Operation): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Operation, newItem: Operation): Boolean {
                return oldItem == newItem
            }
        }
    ) {

    private val HEADER_VIEW_TYPE = 0
    private val ITEM_VIEW_TYPE = 1

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDateOperation: TextView = view.findViewById(R.id.tvDateOperation)
        val tvDateOperationSum: TextView = view.findViewById(R.id.tvDateOperationSum)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivOperation: ImageView = itemView.findViewById(R.id.ivOperation)
        val tvOperationCategory: TextView = itemView.findViewById(R.id.tvOperationCategory)
        val tvOperationAmount: TextView = itemView.findViewById(R.id.tvOperationAmount)
        val tvOperationNameOfPlace: TextView = itemView.findViewById(R.id.tvOperationNameOfPlace)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_VIEW_TYPE -> {
                val headerView = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.header_operations, viewGroup, false)
                HeaderViewHolder(headerView)
            }
            ITEM_VIEW_TYPE -> {
                val itemView = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.item_operation, viewGroup, false)
                ItemViewHolder(itemView)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val sum: Int
        when (viewHolder.itemViewType) {
            HEADER_VIEW_TYPE -> {
                val headerHolder = viewHolder as HeaderViewHolder
                sum = currentList.sumOf { it.amount }
                headerHolder.tvDateOperation.text = currentList.first().date
                headerHolder.tvDateOperationSum.text = "Итого за день: ${sum}"
            }
            ITEM_VIEW_TYPE -> {
                val itemHolder = viewHolder as ItemViewHolder
                val currentItem = getItem(position)
                try {
                    val inputStream: InputStream? =
                        fragment?.context?.assets?.open(currentItem.imageUrl)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    itemHolder.ivOperation.setImageBitmap(bitmap)
                } catch (ex: Exception) {
                    Log.e("mylog", "Error: ${ex.stackTraceToString()}")
                }
                itemHolder.tvOperationCategory.text = currentItem.nameOfCategory
                itemHolder.tvOperationAmount.text = currentItem.amount.toString()
                itemHolder.tvOperationNameOfPlace.text = currentItem.nameOfPlace
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            HEADER_VIEW_TYPE
        } else {
            ITEM_VIEW_TYPE
        }
    }
}
