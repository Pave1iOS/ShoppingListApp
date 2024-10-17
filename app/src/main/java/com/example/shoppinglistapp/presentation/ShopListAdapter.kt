package com.example.shoppinglistapp.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    // для проверки вызовов методов
    var count = 0 // remove

    // создаем лямбда выражение для реализации длинного нажатия
    var shopItemLongClickListener: ((ShopItem) -> Unit)? = null

    // создаем лямбда выражение для реализации обычного нажатия
    var shopItemClickListener: ((ShopItem) -> Unit)? = null

    var shopList = listOf<ShopItem>()
        set(value) {

            //передаем текущий список и новый
            val callback = ShopListDiffCallback(shopList, value)

            // здесь выполнятся все расчеты и сравнения списков
            // работает в главном потоке
            val diffResult = DiffUtil.calculateDiff(callback)

            // что бы адаптер сделал изменения
            // если объект изменился или удалился то обвлять будет не весь список
            // а конкретную ячейку
            diffResult.dispatchUpdatesTo(this)

            // field текущее значение свойства
            // value новое значение свойство
            field = value
        }

    // метод говорит о том как создавать View
    // вызывается при создании группы View (столько сколько отображается на экране
    // + несколько сверху и снизу)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {

        val item = shopList[viewType]

        //можно через конструкуцию when
        val itemLayout = when(viewType) {
            IS_ACTIVE -> R.layout.item_shop_is_active
            IS_INACTIVE -> R.layout.item_shop_is_inactive
            // если в будущем забыть обработать viewType то приложение упадет с этой ошибкой
            else -> throw RuntimeException("view type is unknown $viewType")
        }

        // можно через конструкцию if
//        val itemLayout = if (viewType == IS_ACTIVE) {
//            R.layout.item_shop_is_active
//        } else {
//            R.layout.item_shop_is_inactive
//        }

        // LayoutInflater - нужен что бы преобразовать макет во View элемент
        // с которым можно работать из кода
        val view = LayoutInflater
            .from(parent.context).inflate(itemLayout, parent, false)

        return ShopItemViewHolder(view)
    }

    // количество элементов в коллекции
    override fun getItemCount(): Int {
        return shopList.size
    }

    // как вставить значение внутри View
    // вызывается каждый раз при создании View
    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        // один элемент списка
        val shopItem = shopList[position]

        Log.d("onBindViewHolder", "Вызван ${++count}")

        // берем элементы из holder который хранит View
        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()

        // при долгом нажатии меняет значение CardView
        holder.view.setOnLongClickListener {
            // invoke для опциаональных типов и просто без него для обычных
            shopItemLongClickListener?.invoke(shopItem)
            true
        }

        holder.view.setOnClickListener {
            shopItemClickListener?.invoke(shopItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = shopList[position]

        return if (shopItem.isSelected) {
            return IS_ACTIVE
        } else {
            return IS_INACTIVE
        }
    }

    // вызывается каждый раз при переиспользовании элемента (при скроле)
    override fun onViewRecycled(holder: ShopItemViewHolder) {
        super.onViewRecycled(holder)
    }

    companion object {
        const val IS_ACTIVE = 0
        const val IS_INACTIVE = 1

        const val MAX_POOL = 5
    }

    // ViewHolder нужен для удержания View что бы они пересоздавались минимальное
    // количество раз.
    // если просто то это класс который хранит View
    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        // findViewById будет вызываться один раз при создании ViewHolder
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
    }
}