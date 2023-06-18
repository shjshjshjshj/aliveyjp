package com.tts.yeojeong.`class`

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tts.yeojeong.databinding.ItemHomecardBinding
import com.tts.yeojeong.dataset.Homecarddata
import com.tts.yeojeong.ui.placedetail.placedetailPage

class homecardadapter(context: Context, val itemList: MutableList<Homecarddata>): RecyclerView.Adapter<homecardadapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemHomecardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.bind(item)

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, placedetailPage::class.java)
            intent.putExtra("image", itemList[position].pimage)
            intent.putExtra("name", itemList[position].pname)
            intent.putExtra("addr", itemList[position].padress)
            intent.putExtra("category", itemList[position].category)
            intent.putExtra("tel", itemList[position].tel)
            intent.putExtra("mapx", itemList[position].mapx)
            intent.putExtra("mapy", itemList[position].mapy)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class Holder(var binding: ItemHomecardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Homecarddata) {
            binding.model = item


            Glide.with(itemView.context).load(item.pimage)
                .into(binding.cardImage)
        }
    }

}
