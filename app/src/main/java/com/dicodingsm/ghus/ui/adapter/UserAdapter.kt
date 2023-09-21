package com.dicodingsm.ghus.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicodingsm.ghus.R
import com.dicodingsm.ghus.data.response.Items
import com.dicodingsm.ghus.databinding.ItemRvUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var list = mutableListOf<Items>()

    private var listener : setOnClickListener? = null

    fun setClickListener(call: setOnClickListener) {
        listener = call
    }
    fun setList(listItems: List<Items>) {
        list.clear()
        list.addAll(listItems)
        notifyDataSetChanged()
    }
    inner class UserViewHolder(val bind : ItemRvUserBinding) : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemRvUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = list[position]
        holder.bind.apply {
            Glide.with(holder.itemView.context).load(item.avatarUrl).placeholder(R.drawable.ic_ava).into(civUser)
            tvUsername.text = item.login
        }
        holder.itemView.setOnClickListener { listener?.setOnClicked(item.login) }

    }

    interface setOnClickListener {
        fun setOnClicked(username: String)
    }
}