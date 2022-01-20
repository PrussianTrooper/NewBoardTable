package com.prussian_trooper.project.newboardtable.database

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DbManager {
    val db = Firebase.database.reference

    fun publishAd(){

        db.setValue("Привет")//функция для записи в базу данны
    }
}