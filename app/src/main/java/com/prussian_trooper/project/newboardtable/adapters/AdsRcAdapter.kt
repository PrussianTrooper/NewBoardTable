package com.prussian_trooper.project.newboardtable.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.prussian_trooper.project.newboardtable.data.Ad
import com.prussian_trooper.project.newboardtable.databinding.AdListItemBinding
import java.util.ArrayList

class AdsRcAdapter(val auth: FirebaseAuth): RecyclerView.Adapter<AdsRcAdapter.AdHolder>() {
    val adArray = ArrayList<Ad>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdHolder {//момент создания шаблона в ad_list_item.xml
      val binding = AdListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdHolder(binding, auth)
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


    class AdHolder(val binding: AdListItemBinding, val auth: FirebaseAuth) : RecyclerView.ViewHolder(binding.root) {

        fun setData(ad: Ad){//описание объявления
            binding.apply {
                tvTitile.text = ad.title
                tvDescription.text = ad.description
                tvPrice.text = ad.price

            }
            showEditPanel(isOwner(ad))
        }

        private fun isOwner(ad: Ad): Boolean{
            return ad.uid == auth.uid
        }

        private fun showEditPanel(isOwner: Boolean){
            if (isOwner){
                binding.editPanel.visibility = View.VISIBLE
            } else {
                binding.editPanel.visibility = View.GONE
            }

        }

    }
}