package com.instadoggo.app.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.instadoggo.app.R
import com.instadoggo.app.model.classes.Authentication
import com.instadoggo.app.model.classes.Information
import com.instadoggo.app.model.classes.View

class signup_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_view)

        findViewById<TextView>(R.id.signup_view_login_link).setOnClickListener(){
            startActivity(
                View.change_view(
                    this@signup_activity,
                    login_activity::class.java
                )
            )
        }

        findViewById<Button>(R.id.signup_view_sign_up_button).setOnClickListener(){
            if (
                Information.is_edit_text_empty(
                    this@signup_activity,
                    findViewById<EditText>(R.id.signup_view_full_name_input),
                    "Full Name",
                    true
                ) || Information.is_edit_text_empty(
                    this@signup_activity,
                    findViewById<EditText>(R.id.signup_view_email_input),
                    "Email",
                    true
                ) || Information.is_edit_text_empty(
                    this@signup_activity,
                    findViewById<EditText>(R.id.signup_view_password_input),
                    "Password",
                    true
                )
            ) { }
            else {
                val email = findViewById<EditText>(R.id.signup_view_email_input)
                val full_name = findViewById<EditText>(R.id.signup_view_full_name_input)

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    email.text.toString(),
                    findViewById<EditText>(R.id.signup_view_password_input).text.toString()
                ).addOnCompleteListener(){
                    val userInstance = Authentication.create_user_authentication(
                        this@signup_activity,
                        it,
                        email,
                        findViewById<EditText>(R.id.signup_view_password_input),
                        full_name
                    )
                    if (it.isSuccessful){
                        startActivity(View.change_view(
                            this@signup_activity,
                            add_account_information_activity::class.java,
                            false,
                            mapOf("User ID" to userInstance!!.uid),
                            mapOf("Email" to email.text.toString()),
                            mapOf("Full Name" to full_name.text.toString())
                        ))
                    }
                    else { }
                }
            }
        }
    }
}