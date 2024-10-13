package com.example.shoppinglistapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglistapp.domain.ShopItem
import com.example.shoppinglistapp.domain.ShopListRepository

// object - делает из объекта single ton с единственной реализацией
// методы которые реализуют логику должны помечаться impl (implementation - реализация)
object ShopListRepositoryImpl: ShopListRepository {

    // В дальнейшем при использовании БД эту реализацию следует изменить
    // Создаем изменяемую коллекцию
    private var shopList = mutableListOf<ShopItem>()

    // объект который будет хранить список shopItem в формате LiveData
    // для автоматического обновления данных
    private var shopListLD = MutableLiveData<List<ShopItem>>()

    // Хранение id
    private var id = 0

    // init будет вызван после инициализации переменных
    // в данном случае используем его для тестов ViewModel
    // создаем mock данные
    init {
        for (item in 0 until 10) {
            val shopItem = ShopItem("Item name $item", item, false)
            addShopItem(shopItem)
        }
    }

    override fun addShopItem(item: ShopItem) {
        // если id не установлен то устанавливаем
        if (item.id == ShopItem.UNDEFIND_ID) {
            // присваиваем id для item и увеличиваем счетчик на 1 для следующего
            item.id = id++
        }
        shopList.add(item)
        updateList()
    }

    override fun editShopItem(editItem: ShopItem) {
        val oldShopItem = getShopItem(editItem.id)
        shopList.remove(oldShopItem)
        addShopItem(editItem)
    }

    override fun getShopItem(idItem: Int): ShopItem {
        // find - ищет элемент по условию и если это условие true то возвращает элемент или null
        // throw RuntimeException - выбратывает исключение если элемент равен null и завершает Run
        return shopList.find {
            it.id == idItem
        } ?: throw RuntimeException("Item id - $idItem not found")
    }

    override fun getShoppingList(): LiveData<List<ShopItem>> {
        // toList - возвращает копию массива, а не сам массив
        // если попытаться его поменять то изменится копия
        return shopListLD
    }

    override fun removeShopItem(item: ShopItem) {
        shopList.remove(item)
        updateList()
    }

    private fun updateList() {
        // toList() - создает копию списка
        shopListLD.value = shopList.toList()
    }
}