package com.instadoggo.app.model.classes

import android.content.Context
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

/**
 * File Name: Authentication.kt
 *
 * Last Date Modified: 7/19/2022 by Mitchell M. Gresham
 *
 * Summary: The Authentication class is an abstract class which contains methods the should be used to
 *          authenticate the user when they login, and also create the new account for the user
 *          during sign up.
 *
 * @author Mitchell M. Gresham
 **/
 abstract class Authentication {
    companion object {

        fun create_user_authentication(current_view: Context, input_task: Task<AuthResult>, email_sign_up: EditText, password_sign_up: EditText, full_name_sign_up: EditText): FirebaseUser? {
            if (input_task.isSuccessful){
                prompt_user_signup(current_view, true)
                return input_task.result!!.user!!
            }
            else {
                prompt_user_signup(current_view, false)
                email_sign_up.text.clear()
                password_sign_up.text.clear()
                full_name_sign_up.text.clear()
            }
            return null
        }
        public fun get_user_login(current_view: Context, input_task: Task<AuthResult>, email_login: EditText, password_login: EditText): FirebaseUser? {
            if (input_task.isSuccessful){
                prompt_user_login(current_view, true)
                return input_task.result!!.user!!
            }
            else {
                prompt_user_login(current_view, false)
                email_login.text.clear()
                password_login.text.clear()
            }
            return null
        }
        /**
         * The prompt_user_login function is a public static function of the Authentication class which returns
         * nothing and is used to notify the user through Toast that their login was successful or failed.
         *
         * @param current_view Context -> include a reference to your current view in most cases that will
         *                         be passing in a @thisCurrentView argument.
         *
         * @param login_attempt_result Boolean -> include to represent whether the login attempt was successful or not.
         *
         * @return Void
         */
        private fun prompt_user_login(current_view: Context, login_attempt_result: Boolean){
            when {
                // If login was successful then...
                login_attempt_result -> {
                    Toast.makeText(current_view, "You've Been Logged In Successful. Greetings!", Toast.LENGTH_SHORT).show()
                }
                // If some error has occurred then...
                else -> {
                    Toast.makeText(current_view, "An Error Has Occurred. Please Check Your Information & Internet Connection", Toast.LENGTH_LONG).show()
                }
            }
        }

        /**
         * The prompt_user_signup function is a public static function of the Authentication class which returns
         * nothing and is used to notify the user through Toast that their signup was successful or failed.
         *
         * @param current_view Context -> include a reference to your current view in most cases that will
         *                         be passing in a @thisCurrentView argument.
         *
         * @param login_attempt_result Boolean -> include to represent whether the login attempt was successful or not.
         *
         * @return Void
         */
        private fun prompt_user_signup(current_view: Context, login_attempt_result: Boolean){
            when {
                // If signup was successful then...
                login_attempt_result -> {
                    Toast.makeText(current_view, "You've Signed Up Successfully. Greetings!", Toast.LENGTH_SHORT).show()
                }
                // If some error has occurred then...
                else -> {
                    Toast.makeText(current_view, "An Error Has Occurred. Please Check Your Information & Internet Connection", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

}












