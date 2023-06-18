package com.tts.yeojeong.`class`

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tts.yeojeong.databinding.ItemBookCardBinding
import com.tts.yeojeong.dataset.Bookcarddata
import com.tts.yeojeong.ui.placedetail.placedetailPage

class bookcardadapter(context: Context, val itemList: MutableList<Bookcarddata>): RecyclerView.Adapter<bookcardadapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemBookCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class Holder(var binding: ItemBookCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Bookcarddata) {
            binding.model = item

            binding.bookname.setText(itemList[position].pname)
            binding.bookcategorytext.setText(itemList[position].category)

            Glide.with(itemView.context).load(item.pimage)
                .into(binding.bookImage)
        }
    }

}
