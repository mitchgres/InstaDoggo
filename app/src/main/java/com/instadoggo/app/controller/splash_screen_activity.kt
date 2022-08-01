package com.instadoggo.app.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.instadoggo.app.R
import com.instadoggo.app.model.classes.View

class splash_screen_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_view)

        View.change_view(this@splash_screen_activity, signup_activity::class.java)
        finish()
    }
}