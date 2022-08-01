package com.instadoggo.app.model.data
/**
 * File Name: Profile.kt
 *
 * Last Date Modified: 7/19/2022 by Mitchell M. Gresham
 *
 * Summary: The Profile class is a data class which is used to represent the attributes stored in the
 *          the 'Profile' table of the Firestore Database.
 *
 * @author Mitchell M. Gresham
 **/
data class Profile(

    val f_name: String ?= null,
    val l_name: String ?= null,
    val description: String ?= null,
    val dog_name: String ?= null,
    val email: String ?= null,
    val profile_image: String ?= null,
    val id: String ?= null,
    val date_created: String ?= null,
    val views:Int ?= null,
    val follows:ArrayList<String> ?= null
)
