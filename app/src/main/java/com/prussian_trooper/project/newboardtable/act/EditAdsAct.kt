package com.prussian_trooper.project.newboardtable.act

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import com.fxn.utility.PermUtil
import com.prussian_trooper.project.newboardtable.R
import com.prussian_trooper.project.newboardtable.adapters.ImageAdapter
import com.prussian_trooper.project.newboardtable.model.Ad
import com.prussian_trooper.project.newboardtable.model.DbManager
import com.prussian_trooper.project.newboardtable.databinding.ActivityEditAdsBinding
import com.prussian_trooper.project.newboardtable.dialogs.DialogSpinnerHelper
import com.prussian_trooper.project.newboardtable.frag.FragmentCloseInterface
import com.prussian_trooper.project.newboardtable.frag.ImageListFrag
import com.prussian_trooper.project.newboardtable.utils.CityHelper
import com.prussian_trooper.project.newboardtable.utils.ImagePicker

class EditAdsAct : AppCompatActivity(), FragmentCloseInterface {
    var chooseImageFrag : ImageListFrag? = null
    lateinit var rootElement:ActivityEditAdsBinding
    private val dialog = DialogSpinnerHelper()
    lateinit var imageAdapter : ImageAdapter
    private val dbManager = DbManager(null)
    var editImagePos = 0
    var launcherMultiSelectImage: ActivityResultLauncher<Intent>? = null
    var launcherSingleSelectImage: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(rootElement.root)
        init()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when (requestCode) {
            PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   /* ImagePicker.launcher(this, launcherMultiSelectImage)*/
                } else {
                    Toast.makeText(this, "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun init() {
        imageAdapter = ImageAdapter()
        rootElement.vpImages.adapter = imageAdapter
        launcherMultiSelectImage = ImagePicker.getLauncherForMultiSelectImages(this)
        launcherSingleSelectImage = ImagePicker.getLauncherForSingleImage(this)
    }

    //OnClicks
    fun onClickSelectCountry(view:View){
        val listCountry = CityHelper.getAllCountries(this)
        dialog.showSpinnerDialog(this, listCountry, rootElement.tvCountry)
        if (rootElement.tvCity.text.toString() != getString(R.string.select_city)){
            rootElement.tvCity.text = getString(R.string.select_city)
        }
    }

    fun onClickSelectCity(view:View) {
        val selectedCountry = rootElement.tvCountry.text.toString()
        if (selectedCountry != getString(R.string.select_country)){
        val listCity = CityHelper.getAllCities(selectedCountry, this)
        dialog.showSpinnerDialog(this, listCity, rootElement.tvCity)
    } else {
        Toast.makeText(this, "No country selected", Toast.LENGTH_LONG).show()
        }
   }

    fun onClickSelectCat(view:View) {

            val listCity = resources.getStringArray(R.array.category).toMutableList() as ArrayList
            dialog.showSpinnerDialog(this, listCity, rootElement.tvCat)
    }

    fun onClickGetImages(view:View){
        if (imageAdapter.mainArray.size == 0) {

        ImagePicker.launcher(this,launcherMultiSelectImage, 3)

        } else {

            openChooseImageFrag(null)
            chooseImageFrag?.updateAdapterFromEdir(imageAdapter.mainArray)
        }
    }

    fun onClickPublish(view: View){
        dbManager.publishAd(fillAd())
    }

    private fun fillAd(): Ad {
        val ad: Ad
        rootElement.apply {
            ad = Ad(tvCountry.text.toString(),
                    tvCity.text.toString(),
                    editTel.text.toString(),
                    edIndex.text.toString(),
                    checkBoxWithSend.isChecked.toString(),
                    tvCat.text.toString(),
                    edTitle.text.toString(),
                    edPrice.text.toString(),
                    edDescription.text.toString(), dbManager.db.push().key,
                    dbManager.auth.uid)
        }
        return ad
    }

    override fun onFragClose(list : ArrayList<Bitmap>) {
        rootElement.scrolViewMain.visibility = View.VISIBLE
        imageAdapter.update(list)
        chooseImageFrag = null
    }

    fun openChooseImageFrag(newList: ArrayList<String>?) {

        chooseImageFrag = ImageListFrag(this, newList)
        rootElement.scrolViewMain.visibility = View.GONE
        val fm = supportFragmentManager.beginTransaction()
        fm.replace(R.id.placeHolder, chooseImageFrag!!)
        fm.commit()
    }
}