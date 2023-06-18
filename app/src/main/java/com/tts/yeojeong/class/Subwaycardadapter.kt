package com.tts.yeojeong.`class`

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tts.yeojeong.databinding.ItmeSubrecoBinding
import com.tts.yeojeong.dataset.Subwaycarddata
import com.tts.yeojeong.ui.placedetail.placedetailPage


class Subwaycardadapter (context: Context, val itemList: MutableList<Subwaycarddata>): RecyclerView.Adapter<Subwaycardadapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItmeSubrecoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, placedetailPage::class.java)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class Holder(var binding: ItmeSubrecoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Subwaycarddata) {
            binding.model = item

        }
    }

}
