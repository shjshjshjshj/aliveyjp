package com.tts.yeojeong.`class`

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tts.yeojeong.MainEventListener
import com.tts.yeojeong.databinding.ItemSeleccategoryBinding
import com.tts.yeojeong.dataset.Writereviewcategorydata
import com.tts.yeojeong.writeReview


class writereviewcategoryadapter(context: Context, val itemList: MutableList<Writereviewcategorydata>): RecyclerView.Adapter<writereviewcategoryadapter.Holder>() {

    var selectPos = -1

    inner class ListView(val layout: View) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemSeleccategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        if (item.inner != 4){
            if(selectPos == position) {
                holder.binding.keycardbg.setBackgroundColor(Color.parseColor("#0D70FF"))
                holder.binding.keyworddes.setTextColor(Color.parseColor("#FFFFFF"))
                Log.e("testpos", selectPos.toString())
                keywordinfo.keyword[item.inner - 1] = item.des.toString()
                Log.e("whowingDB", keywordinfo.keyword[0].toString() + keywordinfo.keyword[1].toString() + keywordinfo.keyword[2].toString())
                keywordinfo.bakc()



            } else {
                holder.binding.keycardbg.setBackgroundColor(Color.parseColor("#FFFFFF"))
                holder.binding.keyworddes.setTextColor(Color.parseColor("#000000"))
                Log.e("testpos", selectPos.toString())

            }
        } else {
            var touching = false
            holder.binding.keycardbg.setOnClickListener {
                if(touching == false){
                    holder.binding.keycardbg.setBackgroundColor(Color.parseColor("#0D70FF"))
                    holder.binding.keyworddes.setTextColor(Color.parseColor("#FFFFFF"))


                }
                else{
                    holder.binding.keycardbg.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    holder.binding.keyworddes.setTextColor(Color.parseColor("#000000"))
                    touching = false
                }
            }
        }

        holder.bind(item)

        holder.binding.keycardbg.setOnClickListener{
            var beforePos = selectPos

            selectPos = position

            notifyItemChanged(beforePos)
            notifyItemChanged(selectPos)
        }

    }


    override fun getItemCount(): Int {
        return itemList.size
    }
    private var selectedPosition = RecyclerView.NO_POSITION

    inner class Holder(var binding: ItemSeleccategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener{
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    if (position != selectedPosition) {
                        selectedPosition = position
                        notifyDataSetChanged()
                    }
                }
            }
        }


        fun bind(item: Writereviewcategorydata) {
            binding.model = item

            binding.keyworddes.setText(item.des)
        }
    }


}



object keywordinfo {
    var keyword = Array<String>(4, {""})

    fun bakc(){
        if (keywordinfo.keyword[0] != "" && keywordinfo.keyword[1] != "" && keywordinfo.keyword[2] != ""){
            writeReview().ontrue()
        }
        else {Log.e("notyet", "nottt")}
    }
}
