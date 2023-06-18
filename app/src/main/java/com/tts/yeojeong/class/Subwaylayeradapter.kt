package com.tts.yeojeong.`class`

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tts.yeojeong.R
import com.tts.yeojeong.databinding.LayerRouteBinding
import com.tts.yeojeong.dataset.Subwaylayerdata

class Subwaylayeradapter(val context: Context, val itemList: MutableList<Subwaylayerdata>): RecyclerView.Adapter<Subwaylayeradapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = LayerRouteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class Holder(var binding: LayerRouteBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.layerRoute.setOnClickListener{
                if (binding.innerCard.visibility == View.VISIBLE){
                    binding.innerCard.visibility = View.GONE
                    binding.innerCard.animate().apply {
                        duration = 200
                        rotation(0f)
                    }
                    binding.arrow.rotation = 0f
                }
                else{
                    binding.innerCard.visibility = View.VISIBLE
                    binding.innerCard.animate().apply {
                        duration = 200
                        rotation(0f)
                    }
                    binding.arrow.rotation = 180f
                }
            }
        }

        fun bind(item: Subwaylayerdata) {
            binding.model = item

            when (item.line) {
                "1" -> binding.icRouteunder.setBackgroundColor(context.resources.getColor(R.color.line1))
                "2" -> binding.icRouteunder.setBackgroundColor(context.resources.getColor(R.color.line2))
                "3" -> binding.icRouteunder.setBackgroundColor(context.resources.getColor(R.color.line3))
                "4" -> binding.icRouteunder.setBackgroundColor(context.resources.getColor(R.color.line4))
                "5" -> binding.icRouteunder.setBackgroundColor(context.resources.getColor(R.color.line5))
                "6" -> binding.icRouteunder.setBackgroundColor(context.resources.getColor(R.color.line6))
                "7" -> binding.icRouteunder.setBackgroundColor(context.resources.getColor(R.color.line7))
                "8" -> binding.icRouteunder.setBackgroundColor(context.resources.getColor(R.color.line8))
                "9" -> binding.icRouteunder.setBackgroundColor(context.resources.getColor(R.color.line9))
                else -> binding.icRouteunder.setBackgroundColor(context.resources.getColor(R.color.black))
            }
            when (item.line) {
                "1" -> binding.icRouteic.setBackgroundResource(R.drawable.ics_cicleinnerwhite1)
                "2" -> binding.icRouteic.setBackgroundResource(R.drawable.ics_cicleinnerwhite2)
                "3" -> binding.icRouteic.setBackgroundResource(R.drawable.ics_cicleinnerwhite3)
                "4" -> binding.icRouteic.setBackgroundResource(R.drawable.ics_cicleinnerwhite4)
                "5" -> binding.icRouteic.setBackgroundResource(R.drawable.ics_cicleinnerwhite5)
                "6" -> binding.icRouteic.setBackgroundResource(R.drawable.ics_cicleinnerwhite6)
                "7" -> binding.icRouteic.setBackgroundResource(R.drawable.ics_cicleinnerwhite7)
                "8" -> binding.icRouteic.setBackgroundResource(R.drawable.ics_cicleinnerwhite8)
                "9" -> binding.icRouteic.setBackgroundResource(R.drawable.ics_cicleinnerwhite9)
                else -> binding.icRouteic.setBackgroundResource(R.drawable.ics_cicleinnerwhite1)
            }



            binding.innerCard.adapter = Subwaycardadapter(context, item.innerList)
            binding.innerCard.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

}