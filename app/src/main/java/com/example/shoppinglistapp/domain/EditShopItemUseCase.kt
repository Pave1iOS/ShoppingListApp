package com.example.shoppinglistapp.domain

class EditShopItemUseCase(private val repository: ShopListRepository) {
    fun editShopItem(editItem: ShopItem) {
        repository.editShopItem(editItem)
    }
}