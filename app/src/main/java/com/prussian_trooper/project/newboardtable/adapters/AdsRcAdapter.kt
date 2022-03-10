package com.prussian_trooper.project.newboardtable.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.prussian_trooper.project.newboardtable.MainActivity
import com.prussian_trooper.project.newboardtable.R
import com.prussian_trooper.project.newboardtable.act.EditAdsAct
import com.prussian_trooper.project.newboardtable.model.Ad
import com.prussian_trooper.project.newboardtable.databinding.AdListItemBinding
import java.util.ArrayList

class AdsRcAdapter(val act: MainActivity): RecyclerView.Adapter<AdsRcAdapter.AdHolder>() {
    val adArray = ArrayList<Ad>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdHolder {//момент создания шаблона в ad_list_item.xml
      val binding = AdListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdHolder(binding, act)
    }

    override fun onBindViewHolder(holder: AdHolder, position: Int) {//заполнение
        holder.setData(adArray[position])
    }

    override fun getItemCount(): Int {//размер массива
        return adArray.size
    }

    fun updateAdapter(newList: List<Ad>){
        val diffResult = DiffUtil.calculateDiff(DiffUtilHelper(adArray, newList))
        diffResult.dispatchUpdatesTo(this)
        adArray.clear()
        adArray.addAll(newList)
    }


    class AdHolder(val binding: AdListItemBinding, val act: MainActivity) : RecyclerView.ViewHolder(binding.root) {

        fun setData(ad: Ad) = with(binding) {//описание объявления
            tvTitile.text = ad.title
            tvDescription.text = ad.description
            tvViewCounter.text = ad.viewsCounter
            tvFavCounter.text = ad.favCounter
            if (ad.isFav) {
                ibFav.setImageResource(R.drawable.ic_fav_pressed)
            }else{
                ibFav.setImageResource(R.drawable.ic_fav_normal)

            }
            tvPrice.text = ad.price
            showEditPanel(isOwner(ad))
            ibFav.setOnClickListener{
                act.onFavClicked(ad)
            }
            itemView.setOnClickListener {
                act.onAdViewed(ad)
            }
            ibEditAd.setOnClickListener(onClickEdit(ad))
            ibDeleteAd.setOnClickListener {
                act.onDeleteItem(ad)//удаление объявления
            }
        }

        private fun onClickEdit(ad: Ad): View.OnClickListener{//редактирование объявления
            return View.OnClickListener {
                val editIntent = Intent(act, EditAdsAct::class.java).apply {
                    putExtra(MainActivity.EDIT_STATE, true)
                    putExtra(MainActivity.ADS_DATA, ad)
                }
                act.startActivity(editIntent)
            }
        }

        private fun isOwner(ad: Ad): Boolean{
            return ad.uid == act.mAuth.uid
        }

        private fun showEditPanel(isOwner: Boolean){
            if (isOwner){
                binding.editPanel.visibility = View.VISIBLE
            } else {
                binding.editPanel.visibility = View.GONE
            }

        }

    }

    interface Listener {
        fun onDeleteItem(ad: Ad)
        fun onAdViewed(ad: Ad)
        fun onFavClicked(ad: Ad)
    }

}