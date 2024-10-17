package com.example.shoppinglistapp.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.shoppinglistapp.domain.ShopItem

// проверяет изменения самих объектов а не списков
class ShopItemDiffCallback: DiffUtil.ItemCallback<ShopItem>() {

    // проверяет сами объекты
    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return newItem.id == oldItem.id
    }

    // проверяет содержимое объектов
    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return newItem == oldItem
    }
}