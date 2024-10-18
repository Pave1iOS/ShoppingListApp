package com.example.shoppinglistapp.presentation

import androidx.lifecycle.ViewModel
import com.example.shoppinglistapp.data.ShopListRepositoryImpl
import com.example.shoppinglistapp.domain.AddShopItemUseCase
import com.example.shoppinglistapp.domain.EditShopItemUseCase
import com.example.shoppinglistapp.domain.GetShopItemUseCase
import com.example.shoppinglistapp.domain.ShopItem

class ShopItemViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    val getShopItemUseCase = GetShopItemUseCase(repository)
    val addShopItemUseCase = AddShopItemUseCase(repository)
    val editShopItemUseCase = EditShopItemUseCase(repository)

    fun getShopItem(id: Int) {
        getShopItemUseCase.getShopItem(id)
    }

    fun addShopItem(inputName: String?, inputCount: String?) {

        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValidate = validateField(name, count)

        if (fieldsValidate) {
            val item = ShopItem(name, count, false)
            addShopItemUseCase.addShopItem(item)
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValidate = validateField(name, count)

        if (fieldsValidate) {
            val item = ShopItem(name, count, false)
            editShopItemUseCase.editShopItem(item)
        }
    }

    private fun parseName(inputName: String?): String {
        // обрезает пробелы в строке
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?) : Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateField(name: String, count: Int) : Boolean {

        return if (name.isBlank()) {
            // TODO: show error input name
            false
        } else if (count <= 0) {
            // TODO: show error input count
            false
        } else {
            true
        }
    }
}