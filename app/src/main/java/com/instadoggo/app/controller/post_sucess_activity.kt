package com.instadoggo.app.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.instadoggo.app.R
import com.instadoggo.app.model.classes.View

class post_sucess_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_sucess_view)

        findViewById<Button>(R.id.post_sucess_view_home_button).setOnClickListener(){
            startActivity(View.change_view(
                this@post_sucess_activity,
                home_activity::class.java,
                true,
                mapOf("User ID" to intent.getStringExtra("User ID").toString())
            ))
            finish()
        }
    }
}