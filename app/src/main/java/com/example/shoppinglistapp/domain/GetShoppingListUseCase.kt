package com.example.shoppinglistapp.domain

import androidx.lifecycle.LiveData

class GetShoppingListUseCase(private val repository: ShopListRepository) {
    fun getShoppingList(): LiveData<List<ShopItem>> {
        return repository.getShoppingList()
    }
}