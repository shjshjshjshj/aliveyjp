package com.tts.yeojeong.`class`

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tts.yeojeong.R
import com.tts.yeojeong.databinding.ItemLinelistBinding
import com.tts.yeojeong.dataset.linelistdata
import com.tts.yeojeong.ui.dashboard.activitySelectend


class linelistadapter(context: Context, val itemList: MutableList<linelistdata>): RecyclerView.Adapter<linelistadapter.Holder>() {

    private var selectedItemPosition = -1
    private var selectedLayout: TextView? = null
    var mContext = context

    val items = ArrayList<String>()

    interface ItemClickListener{
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListner : ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
        val touching = holder.itemView.findViewById<TextView>(R.id.listtxt)

        holder.itemView.setOnClickListener{
            itemClickListner.onClick(it, position)
        }

        if (position == selectedItemPosition) {
            touching.setBackgroundColor(Color.parseColor("#DEDEDE"))
        } else {
            touching.setBackgroundColor(Color.WHITE)
        }
        touching.setOnClickListener {

            val currentPosition = holder.adapterPosition

            if (selectedItemPosition == currentPosition) {

                // Item Already Selected

                selectedItemPosition = -1
                selectedLayout?.setBackgroundColor(Color.WHITE)
                selectedLayout = null







            } else {

                // Item New Selected

                if (selectedItemPosition >= 0 || selectedLayout != null) {
                    selectedLayout?.setBackgroundColor(Color.WHITE)
                }

                selectedItemPosition = currentPosition
                selectedLayout = touching
                selectedLayout?.setBackgroundColor(Color.parseColor("#0D70FF"))
                selectedLayout?.setTextColor(Color.parseColor("#FFFFFF"))

                val intent = Intent(holder.itemView.context, activitySelectend::class.java)
                intent.putExtra("stname", itemList[position].name)
                intent.putExtra("stcd", itemList[position].code)
                ContextCompat.startActivity(holder.itemView.context, intent, null)

                Log.e("wwwwwww", itemList[position].name)

            }

            Log.d("ItemAdapter", "getSelectedItem = " + getSelectedItem())
        }

    }

    fun getSelectedItem(): linelistdata? {
        return if (selectedItemPosition >= 0)
            itemList[selectedItemPosition]
        else null
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemLinelistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }




    override fun getItemCount(): Int {
        return itemList.size
    }


    inner class Holder(var binding: ItemLinelistBinding) : RecyclerView.ViewHolder(binding.root) {



        fun bind(item: linelistdata) {
            binding.listtxt.text = itemList.get(position).name
        }

    }

}