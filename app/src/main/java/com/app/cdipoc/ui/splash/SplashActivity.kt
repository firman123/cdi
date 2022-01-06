package com.app.cdipoc.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.cdipoc.R
import com.app.cdipoc.extension.Constant
import com.app.cdipoc.extension.PrefManager
import com.app.cdipoc.ui.home.HomeActivity
import com.app.cdipoc.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val userLogin = PrefManager.getString(this, Constant.PREFERENCE.USER_LOGIN, "")
        if(userLogin.isNullOrEmpty()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}