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
    ListAdapter<Operation, OperationsAdapter.ViewHolder>(
        object : DiffUtil.ItemCallback<Operation>() {
            override fun areItemsTheSame(oldItem: Operation, newItem: Operation): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Operation, newItem: Operation): Boolean {
                return oldItem == newItem
            }
        }
    ) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivOperation: ImageView = itemView.findViewById(R.id.ivOperation)
        val tvOperationCategory: TextView = itemView.findViewById(R.id.tvOperationCategory)
        val tvOperationAmount: TextView = itemView.findViewById(R.id.tvOperationAmount)
        val tvOperationNameOfPlace: TextView = itemView.findViewById(R.id.tvOperationNameOfPlace)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_operation, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        try {
            val inputStream: InputStream? =
                fragment?.context?.assets?.open(currentItem.imageUrl)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            viewHolder.ivOperation.setImageBitmap(bitmap)
        } catch (ex: Exception) {
            Log.e("mylog", "Error: ${ex.stackTraceToString()}")
        }
        viewHolder.tvOperationCategory.text = currentItem.nameOfCategory
        viewHolder.tvOperationAmount.text = currentItem.amount
        viewHolder.tvOperationNameOfPlace.text = currentItem.nameOfPlace
    }
}
