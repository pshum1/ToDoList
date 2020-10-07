package com.example.todolist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.todolist.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SplashScreenActivity : AppCompatActivity() {

    private var user: FirebaseUser? = null
    private val delayedTime: Long = 1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        user = FirebaseAuth.getInstance().currentUser

        var handler = Handler()
        handler.postDelayed({
            checkLogin()
        }, delayedTime)

    }

    private fun checkLogin() {
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))

        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }


}