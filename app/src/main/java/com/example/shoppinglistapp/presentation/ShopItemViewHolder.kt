package com.example.shoppinglistapp.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R

// ViewHolder нужен для удержания View что бы они пересоздавались минимальное
// количество раз.
// если просто то это класс который хранит View
class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    // findViewById будет вызываться один раз при создании ViewHolder
    val tvName = view.findViewById<TextView>(R.id.tv_name)
    val tvCount = view.findViewById<TextView>(R.id.tv_count)
}