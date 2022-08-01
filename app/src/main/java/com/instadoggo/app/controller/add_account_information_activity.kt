package com.instadoggo.app.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.instadoggo.app.R
import com.instadoggo.app.model.classes.Information
import com.instadoggo.app.model.classes.View

class add_account_information_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_account_information_view)

        findViewById<Button>(R.id.add_account_information_view_add_profile_photo_button).setOnClickListener(){
            if (
                Information.is_edit_text_empty(
                    this@add_account_information_activity,
                    findViewById<EditText>(R.id.add_account_information_view_description_input),
                    "Description",
                    true
                ) || Information.is_edit_text_empty(
                    this@add_account_information_activity,
                    findViewById<EditText>(R.id.add_account_information_view_dog_name_input),
                    "Dog Name",
                    true
                )
            ){ }
            else {
                startActivity(
                    View.change_view(
                        this@add_account_information_activity,
                        add_profile_picture_activity::class.java,
                        false,
                        mapOf("User ID" to intent.getStringExtra("User ID")!!),
                        mapOf("Email" to intent.getStringExtra("Email")!!),
                        mapOf("Full Name" to intent.getStringExtra("Full Name")!!),
                        mapOf("Description" to findViewById<EditText>(R.id.add_account_information_view_description_input).text.toString()),
                        mapOf("Dog Name" to findViewById<EditText>(R.id.add_account_information_view_dog_name_input).text.toString())
                    )
                )
                finish()
            }
        }
    }
}