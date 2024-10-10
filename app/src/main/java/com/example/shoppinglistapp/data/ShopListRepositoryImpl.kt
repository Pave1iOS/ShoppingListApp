package com.example.shoppinglistapp.data

import com.example.shoppinglistapp.domain.ShopItem
import com.example.shoppinglistapp.domain.ShopListRepository

// object - делает из объекта single ton с единственной реализацией
// методы которые реализуют логику должны помечаться impl (implementation - реализация)
object ShopListRepositoryImpl: ShopListRepository {

    // В дальнейшем при использовании БД эту реализацию следует изменить
    // Создаем изменяемую коллекцию
    private var shopList = mutableListOf<ShopItem>()

    
    // Хранение id
    private var id = 0

    override fun addShopItem(item: ShopItem) {
        // если id не установлен то устанавливаем
        if (item.id == ShopItem.UNDEFIND_ID) {
            // присваиваем id для item и увеличиваем счетчик на 1 для следующего
            item.id = id++
        }
        shopList.add(item)
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

    override fun getShoppingList(): List<ShopItem> {
        // toList - возвращает копию массива, а не сам массив
        // если попытаться его поменять то изменится копия
        return shopList.toList()
    }

    override fun removeShopItem(item: ShopItem) {
        shopList.remove(item)
    }
}