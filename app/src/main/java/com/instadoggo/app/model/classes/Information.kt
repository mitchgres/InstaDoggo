package com.instadoggo.app.model.classes

import android.content.Context
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
/**
 * File Name: Information.kt
 *
 * Last Date Modified: 7/19/2022 by Mitchell M. Gresham
 *
 * Summary: The Information class is an abstract class which contains methods the should be used to
 *          verify information exists, and that values aren't null.
 *
 * @author Mitchell M. Gresham
 **/
abstract class Information {

    companion object {

        /**
         * The is_edit_text_empty function is a public static function of the Information class which is used to
         * return a Boolean for if the passed in EditText is empty or null, and then use a Boolean value to decide whether
         * Toast should prompt the user that they have not entered the correct values.
         *
         * @param current_view Context -> include a reference to your current view in most cases that will
         *                         be passing in a @thisCurrentView argument.
         *
         * @param edit_text_box EditText -> include a reference to the EditText UI component which you are
         *                          checking to see if empty or null. This should be passed in as an id.
         *
         * @param field_name String -> include a string that is the type of value that should be passed in. For
         *                      example if you want the user to enter their username you would pass in Username for Toast
         *                      to prompt user.
         *
         * @param use_toast Boolean -> include a Boolean to say whether you would like Toast to prompt the user that the information
         *                      which they entered is empty or null. It is true by default.
         *
         * @return Boolean
         */

        public fun is_edit_text_empty(current_view: Context, edit_text_box: EditText, field_name: String, use_toast: Boolean = true): Boolean {
            /*
            If the EditText is empty or null after a trim for spaces, then return true, and if the caller
            has specified that Toast should be used to output a message to the user. Other than that return
            false.
             */
            return if (TextUtils.isEmpty(edit_text_box.text.toString().trim(){ it <= ' '})){
                if (use_toast){
                    Toast.makeText(
                        current_view,
                        "Your Entry For $field_name Is Empty. Please Enter One.",
                        Toast.LENGTH_SHORT
                    )
                }
                true;
            } else {
                false
            }
        }
    }
}











