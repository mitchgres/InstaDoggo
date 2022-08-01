package com.instadoggo.app.model.classes

import android.content.Context
import android.content.Intent
import java.util.*

/**
 * File Name: View.kt
 *
 * Last Date Modified: 7/19/2022 by Mitchell M. Gresham
 *
 * Summary: The View class is an abstract class which contains methods the should be used to navigate
 *          and pass information between views in the application.
 *
 * @author Mitchell M. Gresham
 **/
abstract class View {

    companion object {
        /*
        Every method contained inside the View class is static and therefore should be declared
        inside a companion object.
         */

        /**
         *
         * The change_view function is a public static function of the View class which is used to
         * return an Intent to the caller to change to another view. Mind, you'll have to call
         * startActivity() method on this return for it to run.
         *
         * @param current_view Context -> include a reference to your current view in most cases that will
         *                         be passing in a @thisCurrentView argument.
         *
         * @param target_view Class<*> -> include the view which you would like to be sent to, in most cases
         *                         you will have to pass it as a Java class using TargetView::class.java
         *
         * @param include_flags Boolean -> include a value true or false for whether you would like to include
         *                          flags or not, if not then you can use the back button to return to
         *                          your previous screen, else you cannot. False be default.
         *
         * @return Intent
         */
        public fun change_view(current_view: Context, target_view: Class<*>, include_flags: Boolean = false): Intent {

            // Intent which you'll return to the caller.
            val return_intent = Intent(current_view, target_view)
            if (include_flags){
                // Flags will be added if user has specified.
                return_intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            return return_intent
        }
        /**
         * The change_view function is a public static overloaded function of the View class which
         * is used to return an Intent to the caller to change to another view. This overloaded method
         * takes a variable argument of maps of the type <String, String> that are used to pass in arguments
         * that you would like to pass to the next view.
         *
         * @param current_view Context -> include a reference to your current view in most cases that will
         *                         be passing in a @thisCurrentView argument.
         *
         * @param target_view Class<*> -> include the view which you would like to be sent to, in most cases
         *                         you will have to pass it as a Java class using TargetView::class.java
         *
         * @param include_flags Boolean -> include a value true or false for whether you would like to include
         *                          flags or not, if not then you can use the back button to return to
         *                          your previous screen, else you cannot. False be default.
         *
         * @param arguments vararg -> Map<String, String> -> include a mapOf("reference_name", "value") as your
         *                                          argument which will be passed to the next view as
         *                                          passed arguments, they will be reference using their
         *                                          reference name.
         *
         * @return Intent
         */
        public fun change_view(current_view: Context, target_view: Class<*>, include_flags: Boolean = false, vararg arguments: Map<String, String>): Intent{
            // Intent to be returned to the caller.
            val return_intent = change_view(current_view, target_view, include_flags)
            for (argument in arguments){
                // Uses For-Loop to add arguments to the Intent.
                val argument_key = argument.keys.toList()[0]
                return_intent.putExtra(argument_key, argument[argument_key])
            }
            return return_intent
        }
    }
}