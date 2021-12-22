package com.prussian_trooper.project.newboardtable

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.prussian_trooper.project.newboardtable.act.EditAdsAct
import com.prussian_trooper.project.newboardtable.databinding.ActivityMainBinding
import com.prussian_trooper.project.newboardtable.dialogHelper.DialogConst
import com.prussian_trooper.project.newboardtable.dialogHelper.DialogHelper
import com.prussian_trooper.project.newboardtable.dialogHelper.GoogleAccConst

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var tvAccount: TextView
    private lateinit var rootElement: ActivityMainBinding
    private val dialogHelper = DialogHelper(this)
    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityMainBinding.inflate(layoutInflater)
        val view = rootElement.root
        setContentView(view)
        init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {//While button is active, new activity start working
        if (item.itemId == R.id.id_new_ads) {
            val i = Intent(this, EditAdsAct::class.java)
            startActivity(i)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onStart() {
        super.onStart()
        uiUpdate(mAuth.currentUser)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GoogleAccConst.GOOGLE_SIGN_IN_REQUEST_CODE) {
            //Log.d("MyLog", "Sign in result")
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {

                val account = task.getResult(ApiException::class.java)
                if (account != null){
                    Log.d("MyLog", "Api 0")
                    dialogHelper.accHelper.signInFirebaseWithGoogle(account.idToken!!)
                }

            }catch (e:ApiException){
                Log.d("MyLog", "Api error: ${e.message}")
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun init() {
        setSupportActionBar(rootElement.mainContent.toolbar)
        val toggle = ActionBarDrawerToggle(this, rootElement.drawerLayout, rootElement.mainContent.toolbar, R.string.open, R.string.close)
        rootElement.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        rootElement.navView.setNavigationItemSelectedListener(this)
        tvAccount = rootElement.navView.getHeaderView(0).findViewById(R.id.tvAccountEmail)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.id_my_ads -> {
                /*Проверка работаспособности*/
                Toast.makeText(this, "Pressed id_my_ads", Toast.LENGTH_LONG).show()
            }
            R.id.id_car -> {
                Toast.makeText(this, "Pressed id_car", Toast.LENGTH_LONG).show()
            }
            R.id.id_pc -> {
                Toast.makeText(this, "Pressed id_pc", Toast.LENGTH_LONG).show()
            }
            R.id.id_smart -> {
                Toast.makeText(this, "Pressed id_smart", Toast.LENGTH_LONG).show()
            }
            R.id.id_dm -> {
                Toast.makeText(this, "Pressed id_dm", Toast.LENGTH_LONG).show()
            }
            /*Вход для регистрации*/
            R.id.id_sign_up -> {
                dialogHelper.createSignDialog(DialogConst.SIGN_UP_STATE)
            }
            /*Вход для входа :) */
            R.id.id_sing_in -> {
                dialogHelper.createSignDialog(DialogConst.SIGN_IN_STATE)
            }
            R.id.id_sign_out -> {
                uiUpdate(null)//Если user становится null...
                mAuth.signOut()
                dialogHelper.accHelper.signOutG()
            }
        }
        rootElement.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun uiUpdate(user: FirebaseUser?) {//..., то запускается вот эта функция
      tvAccount.text = if (user == null) {
          resources.getString(R.string.not_reg)
      } else {
          user.email
      }
  }
}
