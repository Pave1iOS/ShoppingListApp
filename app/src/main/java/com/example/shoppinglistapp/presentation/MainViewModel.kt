package com.example.shoppinglistapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglistapp.data.ShopListRepositoryImpl
import com.example.shoppinglistapp.domain.EditShopItemUseCase
import com.example.shoppinglistapp.domain.GetShoppingListUseCase
import com.example.shoppinglistapp.domain.RemoveShopItemUseCase
import com.example.shoppinglistapp.domain.ShopItem

class MainViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShoppingListUseCase = GetShoppingListUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)

    // массив LiveData для <ShopItem> - он будет хранить все объекты ShopItem
    // MutableLiveData - это таже самая LiveData НО в которую мы сами можем вставлять объекты
    // и все подписчики это получают
    val shopList = getShoppingListUseCase.getShoppingList()

    fun editShopItem(item: ShopItem) {

        // создаем копию ShopItem в котором будет переопределен один параметр
        // isSelected - который теперь будет равен противоположному значению
        val newItem = item.copy(isSelected = !item.isSelected)
        val shopItem = editShopItemUseCase.editShopItem(newItem)
    }

    fun removeShopItem(item: ShopItem) {
        removeShopItemUseCase.removeShopItem(item)
    }
}