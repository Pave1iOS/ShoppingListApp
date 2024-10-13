package com.example.shoppinglistapp.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    // Добавление элемента
    fun addShopItem(item: ShopItem)

    // Редактирование элемента
    fun editShopItem(editItem: ShopItem)

    // Получение элемента по id
    fun getShopItem(idItem: Int): ShopItem

    // Получение списка элементов
    fun getShoppingList(): LiveData<List<ShopItem>>

    // Удаление элемента
    fun removeShopItem(item: ShopItem)
}