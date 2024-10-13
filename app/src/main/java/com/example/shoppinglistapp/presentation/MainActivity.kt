package com.example.shoppinglistapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglistapp.R

class MainActivity : AppCompatActivity() {

    // lateinit var позволяет проинициализировать переменную потом
    // и нем нет необходимости создавать null тип
    private lateinit var viewModel: MainViewModel
    var count = 0 // delete

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ViewModelProvider - создаем объект (или находим)
        // [MainViewModel::class.java] - говорим чтоб ViewModelProvider нашла MainViewModel
        // this - говорим что управлять будем из этого класса
        // мы конкретно говоримэй "эй ViewModelProvider найди или создай мне MainViewModel
        // который будет связан с текущей акивностью или фрагментом"
        viewModel = ViewModelProvider(this) [MainViewModel::class.java]

        // наблюдаем за объектом shopList и все его изменения будут прилетать сюда
        viewModel.shopList.observe(this) {
            // выводим в log информацию о каждой полученной здесь shopItem
            Log.d("This is", it.toString())

            if (count == 0) {
                count++
                val item = it[0]
                viewModel.editShopItem(item)
            }

        }
    }
}