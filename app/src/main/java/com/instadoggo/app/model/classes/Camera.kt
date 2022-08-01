package com.instadoggo.app.model.classes

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.util.*


/**
 * File Name: Camera.kt
 *
 * Last Date Modified: 7/19/2022 by Mitchell M. Gresham
 *
 * Summary: The Camera class is an abstract class which contains methods the should be used to
 *          access and use the
 *
 * @author Mitchell M. Gresham
 **/
abstract class Camera {

    companion object {
        /**
         *
         * The prompt_permissions function is a public static function of the Camera class which is used to
         * prompt the user using Toast that they have or haven't enabled permissions for camera access.
         *
         * @param current_view Context -> include a reference to your current view in most cases that will
         *                         be passing in a @thisCurrentView argument.
         * @param input Boolean -> If true then permissions are granted, and if not then tell the user
         *                          to change their app settings.
         *
         */
        public fun prompt_permissions(current_view: Context, input: Boolean) {
            when {
                input -> {
                    Toast.makeText(
                        current_view,
                        "Thank You.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    Toast.makeText(
                        current_view,
                        "You've Denied Permissions. To Continue Please Enable In Settings",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        /**
         *
         * The prompt_request_error function is a public static function of the Camera class which is used to
         * prompt the user using Toast that there has been an error where the passed in camera request code is
         * different then the one stored.
         *
         * @param current_view Context -> include a reference to your current view in most cases that will
         *                         be passing in a @thisCurrentView argument.
         *
         */
        public fun prompt_request_error(current_view: Context) {
            Toast.makeText(
                current_view,
                "An Error Has Occurred While Retrieving Your Data. Please Try Again Later",
                Toast.LENGTH_LONG
            ).show()
        }

        /**
         *
         * The bitmap_to_uri function is a public static function of the Camera class which is used to
         * convert bitmaps given to it into a URI that can be sent to the database.
         *
         * @param current_view Context -> include a reference to your current view in most cases that will
         *                         be passing in a @thisCurrentView argument.
         * @param upload_image Bitmap -> The bitmap which we are converting to a URI for storage in the
         *                          database.
         * @param user_id String -> The user_id is part of the way we ensure that no duplicate images with
         *                          the same name are ever saved as it's impossible for the same user to
         *                          upload an image at the same time, but not impossible for another user to,
         *                          so timestamps aren't enough.
         *
         * @return Uri?
         *
         */
        fun bitmap_to_uri(
            current_view: Context,
            upload_image: Bitmap,
            user_id: String,
            random_name: String
        ): Uri? {
            val bytes = ByteArrayOutputStream()
            upload_image.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path: String = MediaStore.Images.Media.insertImage(
                current_view.getContentResolver(),
                upload_image,
                random_name,
                "Image Taken In InstaDoggo."
            )
            return Uri.parse(path)
        }

    }
}