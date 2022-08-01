package com.instadoggo.app.model.classes

import java.text.SimpleDateFormat
import java.util.*
/**
 * File Name: Greeting.kt
 *
 * File Author: Mitchell M. Gresham
 *
 * Last Date Modified: 7/19/2022 by Mitchell M. Gresham
 *
 * Summary: The Greeting class is an abstract class which contains methods the should be used to
 *          greet the user whenever they open up their home page.
 *
 * @author Mitchell M. Gresham
 **/
abstract class Greeting {
    companion object {
        /**
         * The get_greeting function is a public static function of the Greeting class which is used to
         * return a String that represents a custom greeting for the home page.
         *
         * @param user_name String? -> include the name for the current user which is logged in an then concatenate
         *                      that with the greetings depending on the time.
         *
         * @return String
         */
        public fun get_greeting(user_name: String): String {


            // Sets a constant for the current time when the method is run.
            val current_time = SimpleDateFormat("HH").format(Date()).toInt()

            when {
                /*
                Switch statement which is used to choose what the message is that the user sees.
                 */
                current_time <= 11 -> {
                    return "Good Morning \n$user_name"
                }
                current_time <= 14 -> {
                    return "Good Afternoon \n$user_name"
                }
                current_time <= 21 -> {
                    return "Good Evening \n$user_name"
                }
                current_time <= 24 -> {
                    return "Good Night \n$user_name"
                }
                /*
                If some error has occurred where the time is incorrect, just greet them.
                 */
                else -> {
                    return "Greetings \n$user_name"
                }
            }
        }
    }
}




