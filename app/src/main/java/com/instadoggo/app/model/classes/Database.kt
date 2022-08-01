package com.instadoggo.app.model.classes

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.URL
import java.net.URLConnection
import java.time.LocalDateTime
import java.util.*


/**
 * File Name: Database.kt
 *
 * Last Date Modified: 7/19/2022 by Mitchell M. Gresham
 *
 * Summary: The Database class is an abstract class which contains methods the should be used to
 *          access and query the database for information inside the tables in the database and storage
 *          mediums.
 *
 * @author Mitchell M. Gresham
 **/
abstract class Database {

    companion object {

        /**
         *
         * The upload_firebase_profile function is a public static function of the Database class which is used to
         * upload the Bitmap given to it to the Firebase Storage System, specifically to the profile_pictures directory.
         *
         * @param current_view Context -> include a reference to your current view in most cases that will
         *                         be passing in a @thisCurrentView argument.
         * @param upload_image Bitmap -> The bitmap which is uploaded to database.
         * @param user_id String -> The user_id is part of the way we ensure that no duplicate images with
         *                          the same name are ever saved as it's impossible for the same user to
         *                          upload an image at the same time, but not impossible for another user to,
         *                          so timestamps aren't enough.
         *
         */
        fun upload_firebase_profile(current_view: Context, upload_image: Bitmap, user_id: String): String {

            val random_image_name = "INSTADOGGO_${LocalDateTime.now().toString()}_${user_id}"
            val storageReference = FirebaseStorage.getInstance().getReference();
            val file_path =
                Camera.bitmap_to_uri(current_view, upload_image, user_id, random_image_name)

            val reference = storageReference.child("profile_images/$random_image_name")
            reference.putFile(file_path!!)
                .addOnSuccessListener {
                    Toast.makeText(current_view, "Uploaded", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(current_view, "Failed " + e.message, Toast.LENGTH_SHORT)
                        .show()
                }
            return random_image_name
        }

        fun upload_firebase_post(current_view: Context, upload_image: Bitmap, user_id: String): String {

            val random_image_name = "INSTADOGGO_${LocalDateTime.now().toString()}_${user_id}"
            val storageReference = FirebaseStorage.getInstance().getReference();
            val file_path =
                Camera.bitmap_to_uri(current_view, upload_image, user_id, random_image_name)

            val reference = storageReference.child("post_images/$random_image_name")
            reference.putFile(file_path!!)
                .addOnSuccessListener {
                    Toast.makeText(current_view, "Uploaded", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(current_view, "Failed " + e.message, Toast.LENGTH_SHORT)
                        .show()
                }
            return random_image_name
        }

        /**
         * The get_image function is a public static function of the Database class which is used to return
         * and image from a database given the image name and the directory which its stored in, in the Firebase
         * Storage Database.
         *
         * @param current_view Context -> include a reference to your current view in most cases that will
         *                         be passing in a @thisCurrentView argument.
         *
         * @param image_name String
         *
         * @param directory
         *
         * @return Uri
         *
         */
        fun get_image(current_view: Context, image_name: String, directory: String): Uri {
            val urlReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://instadoggo-a07bd.appspot.com/${directory}/$image_name").downloadUrl
            while (!urlReference.isComplete){
                continue
            }

            return urlReference.result
        }
    }
}