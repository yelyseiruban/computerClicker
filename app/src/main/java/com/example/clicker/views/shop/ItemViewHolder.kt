package com.example.clicker.views.shop

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clicker.R

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val iconImageView: ImageView = itemView.findViewById(R.id.itemIcon)
    val itemTitleTextView: TextView = itemView.findViewById(R.id.itemTitle)
    val itemCountTextView: TextView = itemView.findViewById(R.id.itemCount)
    val itemPriceTextView: TextView = itemView.findViewById(R.id.itemPrice)
}