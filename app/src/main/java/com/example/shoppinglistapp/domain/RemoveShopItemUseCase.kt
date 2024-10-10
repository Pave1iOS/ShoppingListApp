package com.example.shoppinglistapp.domain

class RemoveShopItemUseCase(private val repository: ShopListRepository) {
    fun removeShopItem(item: ShopItem) {
        repository.removeShopItem(item)
    }
}