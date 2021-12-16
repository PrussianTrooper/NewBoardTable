package com.prussian_trooper.project.newboardtable.act

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
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
        dialog.showSpinnerDialog(this, listCountry)

    }
}