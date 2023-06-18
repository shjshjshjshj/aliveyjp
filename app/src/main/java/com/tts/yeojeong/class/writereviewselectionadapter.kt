package com.tts.yeojeong.`class`

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tts.yeojeong.databinding.ItmeSelectkeywordBinding
import com.tts.yeojeong.dataset.Writereviewcategorydata
import com.tts.yeojeong.dataset.Writereviewselectiondata


class writereviewselectionadapter(val context: Context, val itemList: MutableList<Writereviewselectiondata>): RecyclerView.Adapter<writereviewselectionadapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItmeSelectkeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class Holder(var binding: ItmeSelectkeywordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Writereviewselectiondata) {
            binding.model = item


            binding.keycard.adapter = writereviewcategoryadapter(context, item.innerList)
            binding.keycard.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        }
    }

}