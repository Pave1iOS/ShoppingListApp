package com.example.shoppinglistapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value) {
            // field текущее значение свойства
            // value новое значение свойство
            field = value
            // уведомляем что данные изменились и интерфейс должен быть обновлен
            notifyDataSetChanged()
        }

    // метод говорит о том как создавать View
    // вызывается при создании группы View (столько сколько отображается на экране
    // + несколько сверху и снизу)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        // LayoutInflater - нужен что бы преобразовать макет во View элемент
        // с которым можно работать из кода
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_shop_is_active,
            parent,
            false
        )

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

        // берем элементы из holder который хранит View
        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()

        // при долгом нажатии меняет значение CardView
        holder.view.setOnLongClickListener {
            true
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    // вызывается каждый раз при переиспользовании элемента (при скроле)
    override fun onViewRecycled(holder: ShopItemViewHolder) {
        super.onViewRecycled(holder)
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