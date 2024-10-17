package com.example.shoppinglistapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.domain.ShopItem

// ListAdapter - скрывает всю логику работы со списком
// не нужно хранить ссылку на список
class ShopListAdapter :
    ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    // создаем лямбда выражение для реализации длинного нажатия
    var shopItemLongClickListener: ((ShopItem) -> Unit)? = null

    // создаем лямбда выражение для реализации обычного нажатия
    var shopItemClickListener: ((ShopItem) -> Unit)? = null

    // метод говорит о том как создавать View
    // вызывается при создании группы View (столько сколько отображается на экране
    // + несколько сверху и снизу)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {

        val item = getItemViewType(viewType)

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

    // как вставить значение внутри View
    // вызывается каждый раз при создании View
    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        // один элемент списка
        val shopItem = getItem(position)

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
        val shopItem = getItem(position)

        return if (shopItem.isSelected) {
            return IS_ACTIVE
        } else {
            return IS_INACTIVE
        }
    }

    companion object {
        const val IS_ACTIVE = 0
        const val IS_INACTIVE = 1

        const val MAX_POOL = 5
    }
}