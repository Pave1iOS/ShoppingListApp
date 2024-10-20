package com.example.shoppinglistapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglistapp.R

class ShopItemActivity: AppCompatActivity() {

    private lateinit var viewModel: ShopItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_shop_item)

        viewModel = ViewModelProvider(this) [ShopItemViewModel::class.java]

        viewModel.errorInputCount.observe(this) {

        }

        viewModel.errorInputName.observe(this) {

        }
    }
}