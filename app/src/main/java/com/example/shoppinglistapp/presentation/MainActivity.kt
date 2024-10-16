package com.example.shoppinglistapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.domain.ShopItem

class MainActivity : AppCompatActivity() {

    // lateinit var позволяет проинициализировать переменную потом
    // и нам нет необходимости создавать null тип
    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

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

            shopListAdapter.shopList = it
        }
    }

    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)

        // with за место постоянных вызовов rvShopList.recycledViewPool ....
        with(rvShopList) {
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter

            // устанавливаем размер pool для recycler view
            recycledViewPool
                .setMaxRecycledViews(ShopListAdapter.IS_ACTIVE, ShopListAdapter.MAX_POOL)
            recycledViewPool
                .setMaxRecycledViews(ShopListAdapter.IS_INACTIVE, ShopListAdapter.MAX_POOL)
        }

        // меняем состояние ячейки при долгом нажатии через лямбду
        shopListAdapter.shopItemLongClickListener = {
            viewModel.editShopItem(it)
        }

        // логика при нажатии на ячейку
        shopListAdapter.shopItemClickListener = {
            Log.d("setOnClickListener", "Click item ${it.id}")
        }

        shopListAdapter.shopItemOnSwipeListener = {
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    Log.d("onSwiped", "swite ${it.toString()}")
                    viewHolder.adapterPosition
//                    viewModel.removeShopItem(it)
                }
            }).attachToRecyclerView(rvShopList)
        }
    }
}