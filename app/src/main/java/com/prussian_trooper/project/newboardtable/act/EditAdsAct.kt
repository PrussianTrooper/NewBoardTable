package com.prussian_trooper.project.newboardtable.act

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.prussian_trooper.project.newboardtable.R
import com.prussian_trooper.project.newboardtable.databinding.ActivityEditAdsBinding
import com.prussian_trooper.project.newboardtable.dialogs.DialogSpinnerHelper
import com.prussian_trooper.project.newboardtable.utils.CityHelper

class EditAdsAct : AppCompatActivity() {
    lateinit var rootElement:ActivityEditAdsBinding
    private val dialog = DialogSpinnerHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(rootElement.root)
        init()

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
}