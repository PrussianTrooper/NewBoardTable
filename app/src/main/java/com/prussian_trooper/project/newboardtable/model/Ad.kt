package com.prussian_trooper.project.newboardtable.model

import java.io.Serializable

data class Ad(
    val country: String? = null,
    val city: String? = null,
    val tel: String? = null,
    val index: String? = null,
    val withSent: String? = null,
    val category: String? = null,
    val title: String? = null,
    val price: String? = null,
    val description: String? = null,
    val key: String? = null,
    var favCounter: String = "0",
    val uid: String? = null,

    var isFav: Boolean = false,
    val uid1: String? = null,

    var viewsCounter: String = "0",
    var emailCounter: String = "0",
    var callsCounter: String = "0"

):Serializable
