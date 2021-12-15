package com.prussian_trooper.project.newboardtable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import com.prussian_trooper.project.newboardtable.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var rootElement: ActivityMainBinding
    private val dialogHelper = DialogHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityMainBinding.inflate(layoutInflater)
        val view = rootElement.root
        setContentView(view)
        init()

    }

    private fun init(){
        val toggle = ActionBarDrawerToggle(this, rootElement.drawerLayout, rootElement.mainContent.toolbar, R.string.open, R.string.close)
        rootElement.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        rootElement.navView.setNavigationItemSelectedListener(this)
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
                //  uiUpdate(null)//Если user становится null...
                // myAuth.signOut()
            }
        }
        //  rootElement.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


      /*fun uiUpdate(user: FirebaseUser?) {//..., то запускается вот эта функция
      tvAccount.text = if (user == null) {
          resources.getString(R.string.not_reg)
      } else {
          user.email
      }
  }*/
}
