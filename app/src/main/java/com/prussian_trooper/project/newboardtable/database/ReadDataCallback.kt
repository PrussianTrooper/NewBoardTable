package com.prussian_trooper.project.newboardtable.database

import com.prussian_trooper.project.newboardtable.data.Ad

interface ReadDataCallback {
    fun readData(list: List<Ad>)
}