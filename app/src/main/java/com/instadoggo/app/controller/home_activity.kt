package com.instadoggo.app.controller

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.instadoggo.app.R
import com.instadoggo.app.model.classes.Database
import com.instadoggo.app.model.classes.Greeting
import com.instadoggo.app.model.classes.View
import com.instadoggo.app.model.data.Post
import com.instadoggo.app.model.adapter.Post_Adapter
import com.instadoggo.app.model.data.Profile
import com.instadoggo.app.model.data_class_methods.Profile.Companion.get_profile
import com.instadoggo.app.model.enum.Search_Post
import com.instadoggo.app.model.enum.Search_Profile

class home_activity : AppCompatActivity() {

    private lateinit var home_recycle_view: RecyclerView
    private lateinit var adapter: Post_Adapter
    private lateinit var post_array_list: ArrayList<Post>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_view)

        val userInstance = com.instadoggo.app.model.data_class_methods.Profile.get_profile(intent.getStringExtra("User ID").toString(), Search_Profile.ID)

        findViewById<TextView>(R.id.home_view_greeting).text = Greeting.get_greeting(
            userInstance!!.f_name.toString()
        )

        val add_post_button = findViewById<ImageButton>(R.id.home_view_add_post_button)
        val profile_button = findViewById<ImageButton>(R.id.home_view_profile_button)
        val search_button = findViewById<ImageButton>(R.id.home_view_search_button)

        add_post_button.setOnClickListener(){
            startActivity(View.change_view(
                this@home_activity,
                add_post_activity::class.java,
                false,
                mapOf("User ID" to userInstance.id.toString())
            ))
        }
        profile_button.setOnClickListener(){
            startActivity(View.change_view(
                this@home_activity,
                profile_activity::class.java,
                false,
                mapOf("User ID" to userInstance.id.toString())
            ))
        }
        search_button.setOnClickListener(){
            startActivity(View.change_view(
                this@home_activity,
                search_activity::class.java,
                false,
                mapOf("User ID" to userInstance.id.toString())
            ))
        }

        home_recycle_view = findViewById<RecyclerView>(R.id.home_view_recyler_view)
        home_recycle_view.layoutManager = LinearLayoutManager(this)
        home_recycle_view.setHasFixedSize(false)

        post_array_list = arrayListOf()

        var array_list_of_followed = userInstance.follows!!
        for (people in array_list_of_followed){
            post_array_list.addAll(com.instadoggo.app.model.data_class_methods.Post.get_posts(people, Search_Post.ID)
            )
        }


        adapter = Post_Adapter(userInstance, this@home_activity, post_array_list)

        home_recycle_view.adapter = adapter

    }
}