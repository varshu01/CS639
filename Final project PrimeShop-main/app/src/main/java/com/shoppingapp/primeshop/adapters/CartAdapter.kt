package com.shoppingapp.primeshop.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.shoppingapp.primeshop.R
import com.shoppingapp.primeshop.models.CartItems

class CartAdapter(private val context: Context, private val data: List<CartItems>) : BaseAdapter() {

    override fun getCount(): Int = data.size

    override fun getItem(position: Int): Any = data[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.listview_item, parent, false)

        val item: CartItems = getItem(position) as CartItems

        val keyTextView: TextView = view.findViewById(R.id.textKey)
        val valueTextView: TextView = view.findViewById(R.id.textValue)

        keyTextView.text = item.key
        valueTextView.text = item.value

        return view
    }
}