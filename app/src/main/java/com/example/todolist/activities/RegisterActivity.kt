package com.example.todolist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.todolist.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        init()
    }

    private fun init() {
        button_login_from_register.setOnClickListener(this)
        button_register1.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view) {
            button_login_from_register -> {
//                startActivity(Intent(this, LoginActivity::class.java))
                finish()

            }
            button_register1 -> {
                Log.d("authent1", "You got to register_button")
                register()
            }
        }
    }

    private fun register() {
        Log.d("authent1", "You got to register")
        var email = et_register_email.text.toString()
        var password = et_register_password.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    Toast.makeText(
                        applicationContext,
                        "User Registered Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Registration Failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}