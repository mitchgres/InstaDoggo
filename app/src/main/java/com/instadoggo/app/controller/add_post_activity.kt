
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
import com.instadoggo.app.R
import com.instadoggo.app.model.classes.Camera
import com.instadoggo.app.model.classes.Database
import com.instadoggo.app.model.classes.Information
import com.instadoggo.app.model.data_class_methods.Post

class add_post_activity : AppCompatActivity() {

    var has_picture_change = false

    companion object {
        private const val CAMERA_REQUEST_CODE = 4
        private const val CAMERA_PERMISSION_CODE = 5
        private const val IMAGE_PICK_CODE = 6
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_post_view)

        findViewById<Button>(R.id.add_post_view_add_post_button).setOnClickListener() {
            if (
                Information.is_edit_text_empty(
                    this@add_post_activity,
                    findViewById<EditText>(R.id.add_post_view_description_input),
                    "Description",
                    true
                ) || Information.is_edit_text_empty(
                    this@add_post_activity,
                    findViewById<EditText>(R.id.add_post_view_title_input),
                    "Title",
                    true
                ) || !has_picture_change
            ) { }
            else {
                print("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA ${
                    intent.getStringExtra("User ID")!!.toString()
                }")
                Post.create_new_post(
                    this@add_post_activity,
                    intent.getStringExtra("User ID")!!.toString(),
                    Database.upload_firebase_post(
                        this@add_post_activity,
                        findViewById<ImageView>(R.id.add_post_view_image).drawable.toBitmap(),
                        intent.getStringExtra("User ID")!!.toString()
                    ),
                    findViewById<EditText>(R.id.add_post_view_title_input).text.toString(),
                    findViewById<EditText>(R.id.add_post_view_description_input).text.toString()
                )
            }
        }
        findViewById<Button>(R.id.add_post_view_take_photo).setOnClickListener() {
            if (ContextCompat.checkSelfPermission(
                    this@add_post_activity,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                startActivityForResult(
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_REQUEST_CODE
                )
            } else {
                ActivityCompat.requestPermissions(
                    this@add_post_activity,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }
        }
        findViewById<Button>(R.id.add_post_view_from_photos).setOnClickListener() {
            startActivityForResult(
                Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI
                ), IMAGE_PICK_CODE
            )
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
                Camera.prompt_permissions(this@add_post_activity, false)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
            findViewById<ImageView>(R.id.add_post_view_image).setImageBitmap(
                data!!.extras!!.get(
                    "data"
                ) as Bitmap
            )
            has_picture_change = true
        }
        else if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            findViewById<ImageView>(R.id.add_post_view_image).setImageURI(
                data?.data
            )
            has_picture_change = true
        }
        else {
            Camera.prompt_request_error(this@add_post_activity)
        }
    }
}