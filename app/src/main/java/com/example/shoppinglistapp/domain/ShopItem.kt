package com.example.shoppinglistapp.domain

data class ShopItem (
    val name: String,
    val count: Int,
    val isSelected: Boolean,
    var id: Int = UNDEFIND_ID // используем константу
) {
    // создание константы для хранения значения id
    // companion object - реализация свойства для последующего использования в классе
    companion object {
        const val UNDEFIND_ID = -1
    }
}
