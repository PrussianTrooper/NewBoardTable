package com.prussian_trooper.project.newboardtable.dialogHelper

import android.app.AlertDialog
import android.view.View
import com.prussian_trooper.project.newboardtable.MainActivity
import com.prussian_trooper.project.newboardtable.R
import com.prussian_trooper.project.newboardtable.accountHelper.AccountHelper
import com.prussian_trooper.project.newboardtable.databinding.SignDialogBinding

class DialogHelper(act: MainActivity) {
    private val act = act
    private val accHelper = AccountHelper(act)//объект, с помощью которго мы можем регистрироваться

    fun createSignDialog(index:Int){
         val builder = AlertDialog.Builder(act)
         val rootDialogElement = SignDialogBinding.inflate(act.layoutInflater)
         val view = rootDialogElement.root
         builder.setView(view)
         setDialogState(index, rootDialogElement)

        val dialog = builder.create()

         rootDialogElement.btSignUpIn.setOnClickListener{
             setOnClickSignUpIn(index, rootDialogElement, dialog)
         }

         dialog.show()

     }

    private fun setOnClickSignUpIn(index: Int, rootDialogElement: SignDialogBinding, dialog: AlertDialog?) {
        dialog?.dismiss()
        if (index == DialogConst.SIGN_UP_STATE) {

            accHelper.signUpWithEmail(rootDialogElement.edSignEmail.text.toString(),
                rootDialogElement.edSignPassword.text.toString())
        } else {
            accHelper.signInWithEmail(rootDialogElement.edSignEmail.text.toString(),
                rootDialogElement.edSignPassword.text.toString())
        }
    }

    private fun setDialogState(index: Int, rootDialogElement: SignDialogBinding) {
        if (index == DialogConst.SIGN_UP_STATE) {

            rootDialogElement.tvSignTitle.text = act.resources.getString(R.string.ac_sign_up)
            rootDialogElement.btSignUpIn.text = act.resources.getString(R.string.sign_up_action)
        } else {
            rootDialogElement.tvSignTitle.text = act.resources.getString(R.string.ac_sign_in)
            rootDialogElement.btSignUpIn.text = act.resources.getString(R.string.sign_in_action)
            rootDialogElement.btnForgetP.visibility = View.VISIBLE
        }
    }
}