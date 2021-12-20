package com.prussian_trooper.project.newboardtable.act

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.fxn.pix.Options
import com.fxn.pix.Pix
import com.fxn.utility.PermUtil
import com.prussian_trooper.project.newboardtable.R
import com.prussian_trooper.project.newboardtable.databinding.ActivityEditAdsBinding
import com.prussian_trooper.project.newboardtable.dialogs.DialogSpinnerHelper
import com.prussian_trooper.project.newboardtable.frag.FragmentCloseInterface
import com.prussian_trooper.project.newboardtable.frag.ImageListFrag
import com.prussian_trooper.project.newboardtable.utils.CityHelper
import com.prussian_trooper.project.newboardtable.utils.ImagePicker

class EditAdsAct : AppCompatActivity(), FragmentCloseInterface {
    lateinit var rootElement:ActivityEditAdsBinding
    private val dialog = DialogSpinnerHelper()
    private var isImagesPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(rootElement.root)
        init()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == ImagePicker.REQUEST_CODE_GET_IMAGES){
            if (data != null){
                val returnValues = data.getStringArrayListExtra(Pix.IMAGE_RESULTS)
                Log.d("MyLog", "Image : ${returnValues?.get(0)}")
                Log.d("MyLog", "Image : ${returnValues?.get(1)}")
                Log.d("MyLog", "Image : ${returnValues?.get(2)}")
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {


                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ImagePicker.getImages(this,3)
                } else {
                    Toast.makeText(this, "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

    private fun init(){


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
    }else {
        Toast.makeText(this, "No country selected", Toast.LENGTH_LONG).show()
        }
   }

    fun onClickGetImages(view:View){
        rootElement.scrolViewMain.visibility = View.GONE
        val fm = supportFragmentManager.beginTransaction()
        fm.replace(R.id.placeHolder, ImageListFrag(this))
        fm.commit()
    }

    override fun onFragClose() {
        rootElement.scrolViewMain.visibility = View.VISIBLE

    }
}