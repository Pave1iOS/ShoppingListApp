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

//    private lateinit var viewModel: ShopItemViewModel
//
//    private lateinit var tilName: TextInputLayout
//    private lateinit var tilCount: TextInputLayout
//    private lateinit var etName: EditText
//    private lateinit var etCount: EditText
//    private lateinit var btnSave: Button
//
//    // хранит мод (значение intent)
//    // по умолчанию значение не известно
//    private var screenMode = UNKNOWN_MODE
//
//    // хранит id (значение intent)
//    // по умолчанию значение не известно (-1)
//    private var shopItemID = ShopItem.UNDEFIND_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
//
//        //проверяем intent
//        parseIntent()
//
//        // инициализируем view по id
//        createViews()
//
//        // присваиваем значение viewModel
//        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
//
//        // проверяем mod сцены и в зависимости от этого генерируем логику
//        createSceneMode()
//
//        // создаем наблюдатель за полями ввода
//        textChangeListener()
//
//        // метод где лежат все наблюдатели
//        observedViewModel()
    }
//
//    private fun createSceneMode() {
//        // в зависимости от мода запускаем нужное нам поведение экрана
//        when (screenMode) {
//            MODE_EDIT -> runEditScene()
//            MODE_ADD -> runAddScene()
//        }
//    }
//
//    private fun runAddScene() {
//        Log.d("screenMode", "runAddScene")
//
//        btnSave.setOnClickListener {
//            viewModel.addShopItem(etName.text?.toString(), etCount.text.toString())
//        }
//    }
//
//    private fun runEditScene() {
//        Log.d("screenMode", "runEditScene")
//
//        viewModel.getShopItem(shopItemID)
//
//        viewModel.shopItem.observe(this) {
//            etName.setText(it.name)
//            etCount.setText(it.count.toString())
//        }
//
//        btnSave.setOnClickListener {
//            viewModel.editShopItem(etName.text?.toString(), etCount.text.toString())
//        }
//    }
//
//    private fun observedViewModel() {
//        // Отображает ошибку если поле пустое
//        viewModel.errorInputName.observe(this) {
//            val message = if (it) {
//                "Поле не должно быть пустым"
//            } else {
//                null
//            }
//            tilName.error = message
//        }
//
//        // Отображает ошибку если поле пустое
//        viewModel.errorInputCount.observe(this) {
//            val message = if (it) {
//                "Поле не должно быть пустым"
//            } else {
//                null
//            }
//            tilCount.error = message
//        }
//
//        viewModel.finishFlow.observe(this) {
//            finish()
//        }
//    }
//
//    // наблюдатель за вводом текста
//    // сбратывает error если начинаем вводить значения в поле
//    private fun textChangeListener() {
//        etName.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.resetErrorInputName()
//            }
//
//            override fun afterTextChanged(s: Editable?) {}
//        })
//
//        etCount.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.resetErrorInputCount()
//            }
//
//            override fun afterTextChanged(s: Editable?) {}
//        })
//    }
//
//
//    // проверка что все параметры были переданы
//    private fun parseIntent() {
//        // если в intent не будет ключа (параметра) EXTRA_SCREEN_MODE
//        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) throw RuntimeException("screen mode is absent")
//        // создаем мод по ключу
//        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
//        // если этот мод не имеет значений MODE_EDIT и MODE_ADD
//        if (mode != MODE_EDIT && mode != MODE_ADD) throw RuntimeException("mode is absent")
//        // если имеет то присваиваем значение
//        screenMode = mode
//        // если мод редактирования
//        if (mode == MODE_EDIT) {
//            // то там обязательно должен присутствовать параметр id
//            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) throw RuntimeException("id is absent")
//            // присваиваем id из intent и значение по умолчанию
//            shopItemID = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFIND_ID)
//        }
//    }
//
//    private fun createViews() {
//        tilName = findViewById<TextInputLayout>(R.id.til_name)
//        tilCount = findViewById<TextInputLayout>(R.id.til_count)
//        etName = findViewById<EditText>(R.id.et_name)
//        etCount = findViewById<EditText>(R.id.et_count)
//        btnSave = findViewById<Button>(R.id.btn_save)
//    }

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