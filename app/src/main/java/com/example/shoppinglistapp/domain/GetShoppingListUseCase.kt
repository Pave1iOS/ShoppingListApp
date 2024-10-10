package com.example.shoppinglistapp.domain

class GetShoppingListUseCase(private val repository: ShopListRepository) {
    fun getShoppingList(): List<ShopItem> {
        return repository.getShoppingList()
    }
}