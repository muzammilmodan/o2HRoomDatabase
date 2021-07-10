package com.example.o2hroomdemo.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.o2hroomdemo.Model.UserDetails_RoomTable
import com.example.o2hroomdemo.R


class GallaryAdapter(var mContext: Context, var listRecyclerItem: ArrayList<UserDetails_RoomTable>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE = 1


    class ItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        public val imageVw: ImageView

        init {
            imageVw = itemView.findViewById<View>(R.id.imageVw) as ImageView
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        return when (i) {
            TYPE -> {
                val layoutView: View = LayoutInflater.from(viewGroup.context).inflate(
                    R.layout.list_item, viewGroup, false
                )
                ItemViewHolder(layoutView)
            }
            else -> {
                val layoutView: View = LayoutInflater.from(viewGroup.context).inflate(
                    R.layout.list_item, viewGroup, false
                )
                ItemViewHolder(layoutView)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        val viewType = getItemViewType(i)
        when (viewType) {
            TYPE -> {
                val itemViewHolder: ItemViewHolder = viewHolder as ItemViewHolder
                val holidays: UserDetails_RoomTable = listRecyclerItem[i] as UserDetails_RoomTable

                Glide.with(mContext)
                    .load(holidays.profile)
                    .into(viewHolder.imageVw)
            }
            else -> {
                val itemViewHolder: ItemViewHolder = viewHolder as ItemViewHolder
                val holidays: UserDetails_RoomTable = listRecyclerItem[i] as UserDetails_RoomTable
                Glide.with(mContext)
                    .load(holidays.profile)
                    .into(viewHolder.imageVw)
            }
        }
    }

    override fun getItemCount(): Int {
        return listRecyclerItem.size
    }
}