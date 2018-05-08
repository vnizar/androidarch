package voen.example.androidarch.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_user.view.*
import voen.example.androidarch.entity.ItemList
import voen.example.androidarch.entity.User

/**
 * Created by voen on 01/05/18.
 */
class UserViewHolder(itemView: View?, private val listener: (ItemList) -> Unit) : RecyclerView.ViewHolder(itemView), BaseViewHolder {
    override fun bindView(itemList: ItemList) {
        val item = itemList as User
        itemView.tv_name.text = "${item.firstName} ${item.lastName}"
        itemView.setOnClickListener { listener.invoke(itemList) }
    }
}