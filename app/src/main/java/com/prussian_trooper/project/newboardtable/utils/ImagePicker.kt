package com.prussian_trooper.project.newboardtable.utils

import androidx.appcompat.app.AppCompatActivity
import com.fxn.pix.Options
import com.fxn.pix.Pix

object ImagePicker {
    const val REQUEST_CODE_GET_IMAGES = 999
    fun getImages(context: AppCompatActivity, imageCounter : Int){
        var options = Options.init()
            .setRequestCode(REQUEST_CODE_GET_IMAGES)
            .setCount(imageCounter)
            .setFrontfacing(false)
            .setSpanCount(4)
            .setMode(Options.Mode.Picture)
            .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
            .setPath("/pix/images")

        Pix.start(context, options);

    }
}