package com.example.shoppinglistapp.domain

class GetShopItemUseCase(private val repository: ShopListRepository) {
    fun getShopItem(idItem: Int): ShopItem {
        return repository.getShopItem(idItem)
    }
}