package voen.example.androidarch.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import voen.example.androidarch.R
import voen.example.androidarch.entity.ItemList
import voen.example.androidarch.entity.User

/**
 * Created by voen on 01/05/18.
 */
class ItemListAdapter(private val list: List<ItemList>, private val listener: (ItemList) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_USER = 1
        const val TYPE_REPO = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is User -> TYPE_USER
            else -> TYPE_REPO
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BaseViewHolder).bindView(list[position])
    }

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_USER -> UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false), listener)
            else -> RepoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false), listener)
        }
    }
}