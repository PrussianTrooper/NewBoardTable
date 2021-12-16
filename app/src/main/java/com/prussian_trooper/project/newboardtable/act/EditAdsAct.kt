package com.prussian_trooper.project.newboardtable.act

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.prussian_trooper.project.newboardtable.R
import com.prussian_trooper.project.newboardtable.databinding.ActivityEditAdsBinding
import com.prussian_trooper.project.newboardtable.dialogs.DialogSpinnerHelper
import com.prussian_trooper.project.newboardtable.utils.CityHelper

class EditAdsAct : AppCompatActivity() {
    private lateinit var rootElement:ActivityEditAdsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(rootElement.root)
        val listCountry = CityHelper.getAllCountries(this)
        val dialog = DialogSpinnerHelper()
        dialog.showSpinnerDialog(this, listCountry)
    }
}