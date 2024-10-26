package com.example.shoppinglistapp.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    // lateinit var позволяет проинициализировать переменную потом
    // и нам нет необходимости создавать null тип
    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    private var shopItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shopItemContainer = findViewById(R.id.shop_item_container)

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

            // обновляет список элементов
            shopListAdapter.submitList(it)
        }

        // получили кнопку
        val butonAddItem = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        butonAddItem.setOnClickListener {
            if (isSingleScreenMode()) {
                val intent = ShopItemActivity.intentAddItem(this)
                // запускаем intent
                startActivity(intent)
            } else {
                runFragmentContainer(ShopItemFragment.newInstanceAdd())
            }
        }
    }

    // Данная Activity сама реализует данный интерфейс
    // и сама устанавливает как ей поступить при вызове метода
    // здесь мы просто скроем фрагмент
    override fun onEditingFinished() {
        // показываем сообщение пользователю
        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
        // удаляем фрагмент с экрана
        // Если метод вызвать без параметров, то он удалит
        // один последний фрагмент из бэкстека.

        // Если в качестве параметра передать имя фрагмента,
        // то метод удалит из бэкстека все фрагменты, которые
        // были запущены после указанного. В зависимости от
        // переданного флага сам указанный фрагмент может
        // быть также удален из бэкстека
        supportFragmentManager.popBackStack()
    }

    // метод переключает режимы отображения
    // для портретной ориентации и для альбомной
    private fun isSingleScreenMode(): Boolean {
        return shopItemContainer == null
    }

    private fun runFragmentContainer(fragment: ShopItemFragment) {
        // удаляет последний экран из стека
        // а если экрана нет то и удалять ничего не будет (не обязательный метод)
        supportFragmentManager.popBackStack()
        // метод позволяет положить фрагмент в контейнер
        supportFragmentManager.beginTransaction()
            // ложем fragment в контейнер по id
            .replace(R.id.shop_item_container, fragment)
            // ложем экран в стэк (не обязательный метод)
            .addToBackStack(null)
            // сохраняем изменнения
            .commit()
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
        setupLongClickListener()

        // логика при нажатии на ячейку
        setupClickListener()

        // удаление элемента по свайпу
        setupSwipeListener(rvShopList)
    }

    private fun setupSwipeListener(rvShopList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // получаем элемент который свайпнули
                // currentList текущий список над которым работает адаптер
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.removeShopItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }


    private fun setupClickListener() {
        shopListAdapter.shopItemClickListener = {
            if(isSingleScreenMode()) {
                // передаем intent с id
                val intent = ShopItemActivity.intentEditItem(this, it.id)
                // запускаем intent
                startActivity(intent)
            } else {
                runFragmentContainer(ShopItemFragment.newInstanceEdit(it.id))
            }
        }
    }

    private fun setupLongClickListener() {
        shopListAdapter.shopItemLongClickListener = {
            viewModel.editShopItem(it)
        }
    }
}