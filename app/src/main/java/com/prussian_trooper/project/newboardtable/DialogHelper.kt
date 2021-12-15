package com.prussian_trooper.project.newboardtable

import android.app.AlertDialog
import com.prussian_trooper.project.newboardtable.databinding.SignDialogBinding

class DialogHelper(private var act: MainActivity) {

    fun createSignDialog(index:Int){
         val builder = AlertDialog.Builder(act)
         val rootDialogElement = SignDialogBinding.inflate(act.layoutInflater)
         val view = rootDialogElement.root
         if (index == DialogConst.SIGN_UP_STATE) {

             rootDialogElement.tvSignTitle.text = act.resources.getString(R.string.ac_sign_up)
             rootDialogElement.btSignUpIn.text = act.resources.getString(R.string.sign_up_action)
         } else {
             rootDialogElement.tvSignTitle.text = act.resources.getString(R.string.ac_sign_in)
             rootDialogElement.btSignUpIn.text = act.resources.getString(R.string.sign_in_action)

         }

         builder.setView(view)
         builder.show()

     }
}