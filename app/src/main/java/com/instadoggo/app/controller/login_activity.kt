package com.instadoggo.app.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.instadoggo.app.R
import com.instadoggo.app.model.classes.Authentication
import com.instadoggo.app.model.classes.Information
import com.instadoggo.app.model.classes.View

class login_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_view)

        findViewById<TextView>(R.id.login_view_signup_link).setOnClickListener(){
            View.change_view(
                this@login_activity,
                signup_activity::class.java
            )
            finish()
        }

        findViewById<Button>(R.id.login_view_login_button).setOnClickListener(){
            if (
                Information.is_edit_text_empty(
                    this@login_activity,
                    findViewById(R.id.login_view_email_inputy),
                    "Email",
                    true
                ) || Information.is_edit_text_empty(
                    this@login_activity,
                    findViewById(R.id.login_view_password_input),
                    "Password",
                    true
                )
            ){ }
            else {
                val email = findViewById<EditText>(R.id.login_view_email_inputy)

                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    email.text.toString(),
                    findViewById<EditText>(R.id.login_view_password_input).text.toString()
                ).addOnCompleteListener(){
                    val userInstance = Authentication.get_user_login(
                        this@login_activity,
                        it,
                        email,
                        findViewById<EditText>(R.id.login_view_password_input),
                    )
                    if (it.isSuccessful) {
                        startActivity(View.change_view(
                            this@login_activity,
                            home_activity::class.java,
                            true,
                            mapOf("User ID" to userInstance!!.uid)
                        ))
                    }
                    else { }
                }
            }
        }

    }
}