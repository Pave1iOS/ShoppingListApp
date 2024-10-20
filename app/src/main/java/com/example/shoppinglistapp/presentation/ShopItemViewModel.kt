package com.example.shoppinglistapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglistapp.data.ShopListRepositoryImpl
import com.example.shoppinglistapp.domain.AddShopItemUseCase
import com.example.shoppinglistapp.domain.EditShopItemUseCase
import com.example.shoppinglistapp.domain.GetShopItemUseCase
import com.example.shoppinglistapp.domain.ShopItem

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    // свойство для доступа из ViewModel с возможностью изменять его
    private var _errorInputName = MutableLiveData<Boolean>()
    // свойство для доступа из Activity без возможности изменять его
    val errorInputName: LiveData<Boolean>
        // которое получает значения другого свойства
        get() = _errorInputName

    // получение данных об ошибке с числом
    private var _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    // для получения shopItem в activity (getShopItem)
    private var _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    // для того что бы уведомить что экран можно закрывать
    // тип Unit используется когда нам не важен возвращаемый тип
    // нам просто надо уведомить Activity что экран нужно зкарыть
    private var _finishFlow = MutableLiveData<Unit>()
    val finishFlow: LiveData<Unit>
        get() = _finishFlow

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    fun getShopItem(id: Int) {
        val item = getShopItemUseCase.getShopItem(id)
        _shopItem.value = item
    }

    fun addShopItem(inputName: String?, inputCount: String?) {

        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValidate = validateField(name, count)

        if (fieldsValidate) {
            val item = ShopItem(name, count, false)
            addShopItemUseCase.addShopItem(item)
            closeScene()
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValidate = validateField(name, count)

        if (fieldsValidate) {
            // let - выполнят код далее только если свойство не null
            _shopItem.value?.let {
                // создаем копию объекта и присваиваем ему имя и число полученные в методе
                // copy - копируем объект из существующего и меняем в нем name и count
                val item = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(item)
                closeScene()
            }
        }
    }

    private fun parseName(inputName: String?): String {
        // обрезает пробелы в строке
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateField(name: String, count: Int): Boolean {

        return if (name.isBlank()) {
            _errorInputName.value = true
            false
        } else if (count <= 0) {
            _errorInputCount.value = true
            false
        } else {
            true
        }
    }

    private fun closeScene() {
        _finishFlow.value = Unit
    }
}