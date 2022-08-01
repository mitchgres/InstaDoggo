package com.instadoggo.app.model.data
/**
 * File Name: Post.kt
 *
 * Last Date Modified: 7/19/2022 by Mitchell M. Gresham
 *
 * Summary: The Post class is a data class which is used to represent the attributes stored in the
 *          the 'Post' table of the Firestore Database.
 *
 * @author Mitchell M. Gresham
 **/

data class Post(
    var profile_id:String ?= null,
    var post_image:String ?= null,
    var post_title:String ?= null,
    var description:String ?= null,
    var date_posted:String ?= null,
    var views:Int ?= null,
    var likes:Int ?= null
)

