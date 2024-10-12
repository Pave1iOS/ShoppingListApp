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
    val shopList = MutableLiveData<List<ShopItem>>()

    fun  getShopList() {
        val list = getShoppingListUseCase.getShoppingList()
        // присваиваем shopList значение list
        // это всеравно что запись shopList.value = list
        // разница лишь в том что value может вызываться только из главного потока
        // а postValue из любого
        shopList.postValue(list)
    }

    fun editShopItem(item: ShopItem) {

        // создаем копию ShopItem в котором будет переопределен один параметр
        // isSelected - который теперь будет равен противоположному значению
        val newItem = item.copy(isSelected = !item.isSelected)
        val shopItem = editShopItemUseCase.editShopItem(newItem)
        // получаем список для обновления данных
        getShopList()
    }

    fun removeShopItem(item: ShopItem) {
        removeShopItemUseCase.removeShopItem(item)
        // получаем список для обновления данных
        getShopList()
    }
}