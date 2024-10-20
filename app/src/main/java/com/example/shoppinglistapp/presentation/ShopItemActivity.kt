package com.example.shoppinglistapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglistapp.R

class ShopItemActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_shop_item)

        // получаем переданное значение
        val mode = intent.getStringExtra("extra_mode")
        Log.d("ShopItemActivity", mode.toString())
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"

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