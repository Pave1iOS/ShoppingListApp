package com.example.shoppinglistapp.domain

interface ShopListRepository {
    // Добавление элемента
    fun addShopItem(item: ShopItem)

    // Редактирование элемента
    fun editShopItem(editItem: ShopItem)

    // Получение элемента по id
    fun getShopItem(idItem: Int): ShopItem

    // Получение списка элементов
    fun getShoppingList(): List<ShopItem>

    // Удаление элемента
    fun removeShopItem(item: ShopItem)
}