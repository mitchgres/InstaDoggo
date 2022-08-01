package com.instadoggo.app.model.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.instadoggo.app.R
import com.instadoggo.app.controller.other_profile_search_activity
import com.instadoggo.app.model.classes.Database
import com.instadoggo.app.model.data.Profile

class Search_View_Adapter(private val userInstance: String, private val current_view: Context, private val profileList:ArrayList<Profile>): RecyclerView.Adapter<Search_View_Adapter.ProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.profile_search_item, parent, false)

        return ProfileViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {

        val current_profile = profileList[position]

        holder.profileName.text = current_profile.f_name + " " + current_profile.l_name
        holder.dogName.text = "& " + current_profile.dog_name
        holder.item_touch_area.setOnClickListener(){
            (current_view as Activity).startActivity(
                com.instadoggo.app.model.classes.View.change_view(
                    current_view,
                    other_profile_search_activity::class.java,
                    false,
                    mapOf("User ID" to userInstance),
                    mapOf("Profile ID" to current_profile.id.toString())
                )
            )
        }
        Glide.with(holder.profilePicture)
            .load(current_profile.profile_image?.let { Database.get_image(current_view, it, "profile_images") })
            .into(holder.profilePicture)
    }

    override fun getItemCount(): Int {
        return profileList.size
    }
    class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item_touch_area: LinearLayout = itemView.findViewById(R.id.search_item_hor_layout)
        val profileName: TextView = itemView.findViewById(R.id.profile_search_item_name)
        val dogName: TextView = itemView.findViewById(R.id.profile_search_item_dog_name)
        val profilePicture: ImageView = itemView.findViewById(R.id.profile_search_item_profile_picture)
    }
}