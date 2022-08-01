package com.instadoggo.app.model.data_class_methods

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.instadoggo.app.model.adapter.Profile_Posts_Adapter
import com.instadoggo.app.model.classes.Database
import com.instadoggo.app.model.data.Post
import com.instadoggo.app.model.data.Profile
import com.instadoggo.app.model.enum.Search_Profile
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Profile {

    companion object {
        /**
         * The update_profile_post_content function is a public static function of the Profile class which returns
         * nothing updates the given ArrayList whenever an event occurs in the database, which means someone upload, so it
         * can be realtime.
         *
         * @param databaseReference FirebaseFirestore -> Database reference to query the posts and profiles from.
         *
         * @param documentArrayList ArrayList<Post> -> ArrayList updated whenever an event occurs.
         *
         * @param adapter Profile_Posts_Adapter -> Adapts input from database in to displayable format for page.
         *
         */
        fun update_profile_post_content(databaseReference: FirebaseFirestore, documentArrayList: ArrayList<Post>, adapter: Profile_Posts_Adapter){
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
         * The get_profiles function is a public static function of the Profile class which return a ArrayList of Profile
         * object to be displayed. These Profile objects have to fulfill a certain criteria as defined by the search_target, and
         * search_type.
         *
         * @param search_target String -> What you are searching for?
         *
         * @param search_type Search_Profile -> What type of query would you like to perform on the database.
         *
         * @return ArrayList<Profile>
         *
         */
        fun get_profiles(search_target: String, search_type: Search_Profile): ArrayList<Profile> {

            var profile_return_collection: ArrayList<Profile> = arrayListOf()

            var profile_collection = get_profile_as_arraylist(
                FirebaseFirestore.getInstance().collection("Profile").get()
            )

            when {
                search_type == Search_Profile.ID -> {
                    for (docs in profile_collection) {
                        if (docs.id == search_target){
                            profile_return_collection.add(docs)
                        }
                    }
                }
                search_type == Search_Profile.FULL_NAME -> {
                    for (docs in profile_collection){
                        if (docs.f_name + " " + docs.l_name == search_target){
                            profile_return_collection.add(docs)
                        }
                    }
                }
                search_type == Search_Profile.F_NAME -> {
                    for (docs in profile_collection){
                        if (docs.f_name == search_target){
                            profile_return_collection.add(docs)
                        }
                    }
                }
                search_type == Search_Profile.L_NAME -> {
                    for (docs in profile_collection){
                        if (docs.l_name == search_target){
                            profile_return_collection.add(docs)
                        }
                    }
                }
                search_type == Search_Profile.DOG_NAME -> {
                    for (docs in profile_collection){
                        if (docs.dog_name == search_target){
                            profile_return_collection.add(docs)
                        }
                    }
                }
                search_type == Search_Profile.DATE_CREATED -> {
                    for (docs in profile_collection){
                        if (docs.date_created == search_target){
                            profile_return_collection.add(docs)
                        }
                    }
                }
                search_type == Search_Profile.FOLLOWS -> {
                    for (docs in profile_collection){
                        if (docs in profile_collection){
                            profile_return_collection.add(docs)
                        }
                    }
                }
                search_type == Search_Profile.EMAIL -> {
                    for (docs in profile_collection){
                        if (docs in profile_collection){
                            profile_return_collection.add(docs)
                        }
                    }
                }
            }
            return profile_return_collection
        }
        /**
         *
         * The get_profile function is a public static function of the Database class which is used to
         * return a Profile object that is nullable if nothing is found in the database.
         *
         * @param search_target String -> String representation of the attribute of the Profile you
         *                                want to query for in the database.
         * @param search_type Search_Profile -> Uses the Enumeration of Search_Profile to say what kind you
         *                                 want to search for in the database.
         *
         * @return Profile?
         *
         */
        fun get_profile(search_target: String, search_type: Search_Profile): Profile? {
            if (get_profiles(search_target, search_type).size <= 0){
                return null
            }
            else {
                return get_profiles(search_target, search_type)[0]
            }
        }
        /**
         *
         * The create_user_profile function is a public static function of the Database class which given the required
         * attributes creates a new instance of a user in the Firestore Database.
         *
         * @param current_view Context -> include a reference to your current view in most cases that will
         *                         be passing in a @thisCurrentView argument.
         * @param f_name String -> User First Name
         * @param l_name String -> User Last Name
         * @param description String -> User Account Description/Bio
         * @param dog_name String -> User Dog's Name
         * @param email String -> User's Email
         * @param profile_image String -> Link to the profile image of the user in the Firebase Storage Database
         * @param id String -> Uid of the user from Firebase Authentication, used to identification.
         *
         */
        public fun create_user_profile( current_view: Context, f_name: String, l_name: String, description: String, dog_name: String, email: String, profile_image: String, id: String, ){
            val profile_data = hashMapOf(
                "f_name" to f_name,
                "l_name" to l_name,
                "description" to description,
                "dog_name" to dog_name,
                "email" to email,
                "profile_image" to profile_image,
                "id" to id,
                "date-created" to LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString(),
                "follows" to listOf(
                    id
                ),
                "views" to 0
            )
            val profile_reference = FirebaseFirestore.getInstance().collection("Profile").document(id).set(profile_data).addOnSuccessListener {
                Toast.makeText(
                    current_view,
                    "Successful Account Creation",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        fun add_new_follows(current_user: Profile?, user_to_be_followed: Profile?): List<String>{
            var return_list = mutableListOf<String>()
            for (people in current_user?.follows!!){
                return_list.add(people)
            }
            return_list.add(user_to_be_followed?.id.toString())
            return return_list
        }
        fun get_post_of_followed(current_user: Profile?, postArrayList: ArrayList<Post>): ArrayList<Post>{
            var return_array:ArrayList<Post> = arrayListOf()
            for (documents in postArrayList){
                if (documents.profile_id.toString() in current_user!!.follows!!){
                    return_array.add(documents)
                }
            }
            return return_array
        }
        /**
         *
         * The get_profile_as_arraylist function is a private static function of the Database class which is used to
         * return a MutableList<Profile> that represents the various documents stored in the Profile table.
         *
         * @param collection_reference Task<QuerySnapshot> -> include a snapshot taken from the database of the
         *                                                  profile table
         *
         * @return MutableList<Profile>
         *
         */
        private fun get_profile_as_arraylist(collection_reference: Task<QuerySnapshot>): MutableList<Profile> {
            while (!collection_reference.isComplete){
                continue
            }
            return collection_reference.result!!.toObjects(Profile::class.java)
        }
    }

}