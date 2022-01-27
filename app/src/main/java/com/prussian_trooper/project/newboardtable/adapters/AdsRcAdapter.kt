package com.prussian_trooper.project.newboardtable.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prussian_trooper.project.newboardtable.data.Ad
import com.prussian_trooper.project.newboardtable.databinding.AdListItemBinding
import java.util.ArrayList

class AdsRcAdapter: RecyclerView.Adapter<AdsRcAdapter.AdHolder>() {
    val adArray = ArrayList<Ad>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdHolder {//момент создания шаблона в ad_list_item.xml
      val binding = AdListItemBinding.inflate(LayoutInflater.from(parent.context))
        return AdHolder(binding)
    }

    override fun onBindViewHolder(holder: AdHolder, position: Int) {//заполнение
        holder.setData(adArray[position])
    }

    override fun getItemCount(): Int {//размер массива
        return adArray.size
    }

    fun updateAdapter(newList: List<Ad>){
        adArray.clear()
        adArray.addAll(newList)
        notifyDataSetChanged()
    }


    class AdHolder(val binding: AdListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setData(ad: Ad){
            binding.apply {
            //tvTitile.text = ad.
                tvDescription.text = ad.description
                tvPrice.text = ad.price

            }
        }

    }
}