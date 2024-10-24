package com.example.shoppinglistapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {


    // хранит мод (значение intent)
    // по умолчанию значение не известно
    private var screenMode = UNKNOWN_MODE

    // хранит id (значение intent)
    // по умолчанию значение не известно (-1)
    private var shopItemID = ShopItem.UNDEFIND_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        //проверяем intent
        parseIntent()

        // проверяем mod сцены и в зависимости от этого генерируем логику
        createSceneMode()
    }

    private fun createSceneMode() {
        // fragment определяет какой метод вызвать и экране открыть
        val fragment = when (screenMode) {
            MODE_ADD -> ShopItemFragment.newInstanceAdd()
            MODE_EDIT -> ShopItemFragment.newInstanceEdit(shopItemID)
            else -> throw RuntimeException("mode is absent")
        }
        // что бы добавить фрагмент в активити из кода используется класс FragmentManager
        supportFragmentManager.beginTransaction()
            // добавляем фрагмент
            .add(R.id.shop_item_container, fragment).commit() // commit делает что бы transaction
        // выполнилась
    }

        // проверка что все параметры были переданы
    private fun parseIntent() {
        // если в intent не будет ключа (параметра) EXTRA_SCREEN_MODE
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) throw RuntimeException("screen mode is absent")
        // создаем мод по ключу
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        // если этот мод не имеет значений MODE_EDIT и MODE_ADD
        if (mode != MODE_EDIT && mode != MODE_ADD) throw RuntimeException("mode is absent")
        // если имеет то присваиваем значение
        screenMode = mode
        // если мод редактирования
        if (mode == MODE_EDIT) {
            // то там обязательно должен присутствовать параметр id
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) throw RuntimeException("id is absent")
            // присваиваем id из intent и значение по умолчанию
            shopItemID = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFIND_ID)
        }
    }


    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val UNKNOWN_MODE = "unknown"
        private const val FIELD_ERROR = "Error"

        // запускать экран в режиме добавления
        fun intentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        // запускать экран в режиме редактирования
        fun intentEditItem(context: Context, id: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            // говорим в каком режиме открывать окно
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            // передаем id
            intent.putExtra(EXTRA_SHOP_ITEM_ID, id)

            return intent
        }
    }
}