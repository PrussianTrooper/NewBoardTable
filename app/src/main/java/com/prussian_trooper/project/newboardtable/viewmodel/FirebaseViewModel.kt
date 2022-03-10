package com.prussian_trooper.project.newboardtable.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prussian_trooper.project.newboardtable.model.Ad
import com.prussian_trooper.project.newboardtable.model.DbManager

class FirebaseViewModel: ViewModel() {
    private val dbManager = DbManager(null)
    val liveAdsData = MutableLiveData<ArrayList<Ad>>()

    public fun loadAllAds(){
        dbManager.getAllAds(object: DbManager.ReadDataCallback{
            override fun readData(list: ArrayList<Ad>) {
                liveAdsData.value = list
            }
        })
    }

    public fun loadMyAds(){
        dbManager.getMyAds(object: DbManager.ReadDataCallback{
            override fun readData(list: ArrayList<Ad>) {
                liveAdsData.value = list
            }
        })
    }
    fun deleteItem(ad: Ad) {
        dbManager.deleteAd(ad, object: DbManager.FinishWorkListener {
            override fun onFinish() {
                val updatedList = liveAdsData.value
                updatedList?.remove(ad)
                liveAdsData.postValue(updatedList)
            }
        })
    }
}