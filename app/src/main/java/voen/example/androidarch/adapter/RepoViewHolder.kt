package voen.example.androidarch.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_repo.view.*
import voen.example.androidarch.entity.ItemList
import voen.example.androidarch.entity.Repo

/**
 * Created by voen on 01/05/18.
 */
class RepoViewHolder(itemView: View?, private val listener: (ItemList) -> Unit) : RecyclerView.ViewHolder(itemView), BaseViewHolder {
    override fun bindView(itemList: ItemList) {
        val item = itemList as Repo
        itemView.tv_repo_name.text = item.name
        itemView.tv_repo_url.text = item.url
        itemView.setOnClickListener { listener.invoke(itemList) }
    }
}