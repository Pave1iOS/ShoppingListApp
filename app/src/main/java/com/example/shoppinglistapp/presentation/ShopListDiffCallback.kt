package com.example.shoppinglistapp.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.shoppinglistapp.domain.ShopItem

// каждый раз при обновлении данных в списке создается экземпляр этого класса
class ShopListDiffCallback(
    private val oldList: List<ShopItem>,
    private val newList: List<ShopItem>
) : DiffUtil.Callback() {
    // количесто элементов старого списка
    override fun getOldListSize(): Int {
        return oldList.size
    }

    // количесто элементов нового списка
    override fun getNewListSize(): Int {
        return newList.size
    }

    // адаптер понимает что это тот же самый элемент у которого поменялись поля
    // проверяет сами объекты
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItemID = newList[newItemPosition].id
        val oldItemID = oldList[oldItemPosition].id

        return newItemID == oldItemID
    }

    // проверяет содержимое объектов
    // если поля объектов изменились то объект нужно перерисовать
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = newList[newItemPosition]
        val oldItem = oldList[oldItemPosition]

        return newItem == oldItem
    }
}