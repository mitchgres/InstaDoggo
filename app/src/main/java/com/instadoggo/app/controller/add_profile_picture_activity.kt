package com.instadoggo.app.controller

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.instadoggo.app.R
import com.instadoggo.app.model.classes.Camera
import com.instadoggo.app.model.classes.Database
import com.instadoggo.app.model.classes.View
import com.instadoggo.app.model.data_class_methods.Profile


class add_profile_picture_activity : AppCompatActivity() {

    var has_picture_changed = false

    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
        private const val IMAGE_PICK_CODE = 3
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_profile_picture_view)

        findViewById<Button>(R.id.add_profile_picture_view_take_picture_button).setOnClickListener() {
            if (ContextCompat.checkSelfPermission(
                    this@add_profile_picture_activity,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_REQUEST_CODE)
            } else {
                ActivityCompat.requestPermissions(
                    this@add_profile_picture_activity,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }
        }

        findViewById<Button>(R.id.add_profile_picture_view_upload_image_button).setOnClickListener() {
            startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), IMAGE_PICK_CODE)
        }

        findViewById<Button>(R.id.add_profile_picture_view_done_button).setOnClickListener(){
            when {
                has_picture_changed -> {
                    findViewById<ImageView>(R.id.add_profile_picture_view_post_image)

                    val profile_picture = Database.upload_firebase_profile(
                        this@add_profile_picture_activity,
                        findViewById<ImageView>(R.id.add_profile_picture_view_post_image).drawable.toBitmap(),
                        intent.getStringExtra("User ID").toString()
                    )

                    Profile.create_user_profile(
                        this@add_profile_picture_activity,
                        intent.getStringExtra("Full Name")?.toString()!!.trim()?.split(' ')!![0],
                        intent.getStringExtra("Full Name")?.toString()!!.trim()?.split(' ')!![1],
                        intent.getStringExtra("Description")?.toString()!!.trim(),
                        intent.getStringExtra("Dog Name").toString()!!.trim(),
                        intent.getStringExtra("Email").toString()!!.trim(),
                        profile_picture,
                        intent.getStringExtra("User ID").toString().trim()
                    )
                    startActivity(View.change_view(
                        this@add_profile_picture_activity,
                        home_activity::class.java,
                    true,
                        mapOf("User ID" to intent.getStringExtra("User ID").toString())
                    ))
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
                startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_REQUEST_CODE)
            }
            else {
                Camera.prompt_permissions(this@add_profile_picture_activity, false)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
            findViewById<ImageView>(R.id.add_profile_picture_view_post_image).setImageBitmap(
                data!!.extras!!.get(
                    "data"
                ) as Bitmap
            )
            has_picture_changed = true
        }
        else if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            findViewById<ImageView>(R.id.add_profile_picture_view_post_image).setImageURI(
                data?.data
            )
            has_picture_changed = true
        }
        else {
            Camera.prompt_request_error(this@add_profile_picture_activity)
        }
    }
}