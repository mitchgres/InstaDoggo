package com.instadoggo.app.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.instadoggo.app.R
import com.instadoggo.app.model.classes.Database
import com.instadoggo.app.model.data.Post
import com.instadoggo.app.model.data.Profile
import com.instadoggo.app.model.adapter.Profile_Posts_Adapter
import com.instadoggo.app.model.enum.Search_Post
import com.instadoggo.app.model.enum.Search_Profile

class other_profile_search_activity : AppCompatActivity() {

    private lateinit var postRecyclerView: RecyclerView
    private lateinit var adapter: Profile_Posts_Adapter
    private lateinit var postArrayList: ArrayList<Post>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.other_account_profile_view)

        val userInstance: Profile? = com.instadoggo.app.model.data_class_methods.Profile.get_profile(intent.getStringExtra("User ID").toString(), Search_Profile.ID)
        val profile_id: Profile? = com.instadoggo.app.model.data_class_methods.Profile.get_profile(intent.getStringExtra("Profile ID").toString(), Search_Profile.ID)

        findViewById<TextView>(R.id.other_account_profile_view_name).text = "${profile_id?.f_name.toString()} ${profile_id?.l_name.toString()}"
        findViewById<TextView>(R.id.other_account_profile_view_description).text = profile_id?.description.toString()
        Glide.with(findViewById<ImageView>(R.id.other_account_profile_view_profile_picture))
            .load(profile_id!!.profile_image?.let { Database.get_image(this@other_profile_search_activity, it, "profile_images") })
            .into(findViewById<ImageView>(R.id.other_account_profile_view_profile_picture))
        findViewById<Button>(R.id.other_account_profile_view_follow_button).setOnClickListener(){
            FirebaseFirestore.getInstance().collection("Profile").document(intent.getStringExtra("User ID").toString()).update(
                "follows", com.instadoggo.app.model.data_class_methods.Profile.add_new_follows(userInstance, profile_id)
            )
        }


        postRecyclerView = findViewById<RecyclerView>(R.id.other_account_profile_view_recycler_view)
        postRecyclerView.layoutManager = LinearLayoutManager(this)
        postRecyclerView.setHasFixedSize(false)
        postArrayList = com.instadoggo.app.model.data_class_methods.Post.get_posts(profile_id.id.toString(), Search_Post.ID)
        adapter = Profile_Posts_Adapter(profile_id, this@other_profile_search_activity, postArrayList)
        postRecyclerView.adapter = adapter
    }
}