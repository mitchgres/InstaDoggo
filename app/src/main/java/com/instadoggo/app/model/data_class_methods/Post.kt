package com.instadoggo.app.model.data_class_methods

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.instadoggo.app.controller.post_sucess_activity
import com.instadoggo.app.model.adapter.Post_Adapter
import com.instadoggo.app.model.classes.Database
import com.instadoggo.app.model.classes.View
import com.instadoggo.app.model.data.Post
import com.instadoggo.app.model.data.Profile
import com.instadoggo.app.model.enum.Search_Post
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class Post {
 companion object {
     /**
      *
      * The create_new_post function is a public static function of the Post class which is used to
      * create a new Post in the Post collection of the Firestore Database.
      *
      * @param current_view Context -> include a reference to your current view in most cases that will
      *                         be passing in a @thisCurrentView argument.
      * @param userInstance String -> ID of the current user that is using the app as given by authentication
      * @param post_image String -> link to the photo saved in the Firebase Storage system
      * @param title String
      * @param description String
      *
      */
     fun create_new_post(current_view: Context, userInstance: String, post_image: String, title: String, description: String, ){
         val data = hashMapOf(
             "profile_id" to userInstance,
             "post_image" to post_image,
             "post_title" to title,
             "description" to description,
             "date-created" to LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString(),
             "views" to 0,
             "likes" to 0
         )
         FirebaseFirestore.getInstance().collection("Post").document(UUID.randomUUID().toString()).set(data).addOnCompleteListener {
         }
             .addOnSuccessListener {
             Toast.makeText(
                 current_view,
                 "Successful Account Post",
                 Toast.LENGTH_SHORT
             ).show()
             (current_view as Activity).startActivity(
                 View.change_view(
                     current_view,
                     post_sucess_activity::class.java,
                     true,
                     mapOf("User ID" to userInstance)
                 )
             )
             (current_view as Activity).finish()
         }
     }
     /**
      *
      * The get_post function is a public static function of the Database class which is used to
      * return a Post object that is nullable if nothing is found in the database.
      *
      * @param search_target String -> String representation of the attribute of the Profile you
      *                                want to query for in the database.
      * @param search_type Search_Post -> Uses the Enumeration of Search_Profile to say what kind you
      *                                 want to search for in the database.
      *
      * @return Post?
      *
      */
     public fun get_post(search_target: String, search_type: Search_Post): Post? {

         var post_collection = get_post_as_arraylist(
             FirebaseFirestore.getInstance().collection("Post").get()
         )

         when {
             search_type == Search_Post.ID -> {
                 for (docs in post_collection){
                     if (docs.profile_id == search_target){
                         return docs
                     }
                 }
             }
             search_type == Search_Post.DATE_POSTED -> {
                 for (docs in post_collection){
                     if (docs.date_posted == search_target){
                         return docs
                     }
                 }
             }
             search_type == Search_Post.VIEWS -> {
                 for (docs in post_collection){
                     if (docs.views == search_target.toInt()){
                         return docs
                     }
                 }
             }
             search_type == Search_Post.LIKES -> {
                 for (docs in post_collection){
                     if (docs.likes == search_target.toInt()){
                         return docs
                     }
                 }
             }
         }
         return null
     }

     public fun get_posts(search_target: String, search_type: Search_Post): ArrayList<Post> {
        var return_array = arrayListOf<Post>()
         var post_collection = get_post_as_arraylist(
             FirebaseFirestore.getInstance().collection("Post").get()
         )

         when {
             search_type == Search_Post.ID -> {
                 for (docs in post_collection){
                     if (docs.profile_id == search_target){
                         return_array.add(docs)
                     }
                 }
             }
             search_type == Search_Post.DATE_POSTED -> {
                 for (docs in post_collection){
                     if (docs.date_posted == search_target){
                         return_array.add(docs)
                     }
                 }
             }
             search_type == Search_Post.VIEWS -> {
                 for (docs in post_collection){
                     if (docs.views == search_target.toInt()){
                         return_array.add(docs)
                     }
                 }
             }
             search_type == Search_Post.LIKES -> {
                 for (docs in post_collection){
                     if (docs.likes == search_target.toInt()){
                         return_array.add(docs)
                     }
                 }
             }
         }
         return return_array
     }

     /**
      * The update_post_content
      */
     fun update_post_content(
         userInstance: com.instadoggo.app.model.data.Profile?,
         databaseReference: FirebaseFirestore,
         documentArrayList: ArrayList<Post>,
         adapter: Post_Adapter
     ){
         databaseReference.collection("Post").addSnapshotListener(
             object : EventListener<QuerySnapshot> {
                 @SuppressLint("NotifyDataSetChanged")
                 override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?)
                 {
                     if (error != null){
                         // We Have Error
                         Log.e("FIRESTORE ERROR: ", error.message.toString())
                         return // END
                     }

                     else
                     {
                         for (doc_change: DocumentChange in value?.documentChanges!!){
                             if (doc_change.type == DocumentChange.Type.ADDED){
                                 documentArrayList.add(doc_change.document.toObject(Post::class.java))
                             }
                         }
                     }
                     adapter.notifyDataSetChanged()
                 }
             }
         )
     }
     /**
      *
      * The get_profile_as_arraylist function is a private static function of the Database class which is used to
      * return a MutableList<Post> that represents the various documents stored in the Post table.
      *
      * @param collection_reference Task<QuerySnapshot> -> include a snapshot taken from the database of the
      *                                                  post table
      *
      * @return MutableList<Post>
      *
      */
     private fun get_post_as_arraylist(collection_reference: Task<QuerySnapshot>): MutableList<Post> {
         while (!collection_reference.isComplete){
             continue
         }
         return collection_reference.result!!.toObjects(Post::class.java)
     }
 }
}