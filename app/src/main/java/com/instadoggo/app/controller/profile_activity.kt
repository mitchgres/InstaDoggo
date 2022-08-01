package com.instadoggo.app.controller

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.instadoggo.app.R
import com.instadoggo.app.model.classes.Camera
import com.instadoggo.app.model.classes.Database
import com.instadoggo.app.model.classes.View
import com.instadoggo.app.model.data_class_methods.Profile
import com.instadoggo.app.model.enum.Search_Profile

class profile_activity : AppCompatActivity() {

    var has_picture_changed = false

    companion object {
       private const val CAMERA_REQUEST_CODE = 4
        private const val CAMERA_PERMISSION_CODE = 5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_view)

        val userInstance = Profile.get_profile(
            intent.getStringExtra("User ID").toString(),
            Search_Profile.ID,
        )

        findViewById<Button>(R.id.profile_view_update_picture).setOnClickListener() {
            if (ContextCompat.checkSelfPermission(
                    this@profile_activity,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                startActivityForResult(
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE),
                    CAMERA_REQUEST_CODE
                )
            } else {
                ActivityCompat.requestPermissions(
                    this@profile_activity,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }
        }

        Glide.with(findViewById<ImageView>(R.id.profile_view_profile_picture))
            .load(userInstance?.profile_image?.let { Database.get_image(this@profile_activity, it, "profile_images") })
            .into(findViewById<ImageView>(R.id.profile_view_profile_picture))
        findViewById<EditText>(R.id.profile_view_full_name_input).setText("${userInstance?.f_name.toString()} ${userInstance?.l_name.toString()}")
        findViewById<EditText>(R.id.profile_view_description_input).setText(userInstance?.description.toString())
        findViewById<EditText>(R.id.profile_view_dog_name_input).setText(userInstance?.dog_name.toString())

        findViewById<Button>(R.id.profile_view_done_button).setOnClickListener(){
            when {
                findViewById<EditText>(R.id.profile_view_full_name_input).text.toString().trim() != "${userInstance?.f_name.toString()} ${userInstance?.l_name.toString()}" ->
                {
                    FirebaseFirestore.getInstance().collection("Profile").document(intent.getStringExtra("User ID").toString()).update(
                        "f_name", findViewById<EditText>(R.id.profile_view_full_name_input).text.toString().trim().split(' ')[0],
                        "l_name", findViewById<EditText>(R.id.profile_view_full_name_input).text.toString().trim().split(' ')[1]
                    )}
                findViewById<EditText>(R.id.profile_view_description_input).text.toString().trim() != userInstance?.description.toString() -> {
                    FirebaseFirestore.getInstance().collection("Profile").document(intent.getStringExtra("User ID").toString()).update(
                        "description", findViewById<EditText>(R.id.profile_view_description_input).text.toString().trim()
                    )
                }
                findViewById<EditText>(R.id.profile_view_dog_name_input).text.toString().trim() != userInstance?.dog_name -> {
                    FirebaseFirestore.getInstance().collection("Profile").document(intent.getStringExtra("User ID").toString()).update(
                        "dog_name", findViewById<EditText>(R.id.profile_view_dog_name_input).text.toString().trim()
                    )
                }
                has_picture_changed -> {
                    FirebaseFirestore.getInstance().collection("Profile").document(intent.getStringExtra("User ID").toString()).update(
                        "profile_image", Database.upload_firebase_profile(
                            this@profile_activity,
                            findViewById<ImageView>(R.id.profile_view_profile_picture).drawable.toBitmap(),
                            intent.getStringExtra("User ID").toString()
                        )
                    )
                }
                else -> {
                    View.change_view(
                        this@profile_activity,
                        home_activity::class.java,
                        false,
                        mapOf("User ID" to userInstance.toString())
                    )
                }
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE),
                    CAMERA_REQUEST_CODE
                )
            }
            else {
                Camera.prompt_permissions(this@profile_activity, false)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
            findViewById<ImageView>(R.id.profile_view_profile_picture).setImageBitmap(
                data!!.extras!!.get(
                    "data"
                ) as Bitmap
            )
            has_picture_changed = true
        }
        else {
            Camera.prompt_request_error(this@profile_activity)
        }
    }
}