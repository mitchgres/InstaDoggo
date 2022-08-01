package com.instadoggo.app.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.instadoggo.app.R
import com.instadoggo.app.model.classes.Information
import com.instadoggo.app.model.data.Profile
import com.instadoggo.app.model.adapter.Search_View_Adapter
import com.instadoggo.app.model.enum.Search_Profile

class search_activity : AppCompatActivity() {

    private lateinit var profileRecyclerView: RecyclerView
    private lateinit var adapter: Search_View_Adapter
    private lateinit var profileList: ArrayList<Profile>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_view)

        findViewById<ImageButton>(R.id.search_view_search_button).setOnClickListener(){
            when {
                Information.is_edit_text_empty(this@search_activity,
                    findViewById(R.id.search_view_input_bar),
                    "Search") -> { }

                else -> {
                    val inputFromUser = findViewById<EditText>(R.id.search_view_input_bar).text.toString().trim(' ')


                    val resultFName = com.instadoggo.app.model.data_class_methods.Profile.get_profiles(inputFromUser, Search_Profile.F_NAME,)
                    val resultLName = com.instadoggo.app.model.data_class_methods.Profile.get_profiles(inputFromUser, Search_Profile.L_NAME,)
                    val resultFullName = com.instadoggo.app.model.data_class_methods.Profile.get_profiles(inputFromUser, Search_Profile.FULL_NAME,)
                    val resultDogName = com.instadoggo.app.model.data_class_methods.Profile.get_profiles(inputFromUser, Search_Profile.DOG_NAME,)

                    profileList = arrayListOf()


                    when {
                        !resultFName.isEmpty() -> {
                            profileList.addAll(resultFName)
                        }
                        !resultLName.isEmpty() -> {
                            profileList.addAll(resultLName)
                        }
                        !resultFullName.isEmpty() -> {
                            profileList.addAll(resultFullName)
                        }
                        !resultDogName.isEmpty() -> {
                            profileList.addAll(resultDogName)
                        }
                    }

                    profileRecyclerView = findViewById(R.id.search_view_recycler)
                    profileRecyclerView.layoutManager = LinearLayoutManager(this)
                    profileRecyclerView.setHasFixedSize(true)

                    adapter = Search_View_Adapter(intent.getStringExtra("User ID").toString(), this@search_activity, profileList)
                    profileRecyclerView.adapter = adapter
                }
            }
        }
    }
}