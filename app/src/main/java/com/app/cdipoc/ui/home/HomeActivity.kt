package com.app.cdipoc.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.app.cdipoc.R
import com.app.cdipoc.databinding.ActivityHomeBinding
import com.app.cdipoc.dialog.KeyDialog
import com.app.cdipoc.dialog.VerifyDialog
import com.app.cdipoc.extension.Constant
import com.app.cdipoc.extension.PrefManager
import com.app.cdipoc.ui.camera.FaceCameraActivity
import com.app.cdipoc.ui.camera.KtpCameraActivity
import com.app.cdipoc.ui.contact.ContactUsActivity
import com.app.cdipoc.ui.dukcapil.DukcapilActivity
import com.app.cdipoc.ui.login.LoginActivity
import com.app.cdipoc.ui.verification.VerificationActivity


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
        actionListener()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_logout) {
            PrefManager.logOut(this)
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initToolbar() {
        binding.toolbar.title = ""
        setSupportActionBar(binding.toolbar)

    }

    private fun actionListener() {

        binding.btnOcr.setOnClickListener {
            startActivity(Intent(this, KtpCameraActivity::class.java))
        }

        binding.btnPassive.setOnClickListener {
            val appId = PrefManager.getString(this, Constant.HEADER.APP_ID, "")
            if (appId.isNullOrEmpty()) {
                val dialog = KeyDialog(this)
                dialog.showDialog()
            } else {
                startActivity(
                    Intent(this, FaceCameraActivity::class.java)
                        .putExtra("type", "passive")
                )
            }
        }

        binding.btnEnroll.setOnClickListener {
            startActivity(
                Intent(this, VerificationActivity::class.java)
                    .putExtra("type", "enroll_data")
            )
        }

        binding.btnBiometric.setOnClickListener {
            startActivity(
                Intent(this, VerificationActivity::class.java)
                    .putExtra("type", "biometric")
            )
        }

        binding.btnDukcapil.setOnClickListener {
            if (PrefManager.getString(this, Constant.DUKCAPIL_VERIFY.URL, "").isNullOrEmpty()) {
                val verifyDialog = VerifyDialog(this)
                verifyDialog.showDialog()
            } else {
                startActivity(
                    Intent(this, DukcapilActivity::class.java)
                )
            }
        }

        binding.btnLocal.setOnClickListener {
            startActivity(
                Intent(this, DukcapilActivity::class.java)
                    .putExtra("type", "local_verify")
            )
        }

        binding.tvContact.setOnClickListener {
            startActivity(Intent(this, ContactUsActivity::class.java))
        }
    }
}