package com.instadoggo.app.model.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.instadoggo.app.R
import com.instadoggo.app.model.classes.Database
import com.instadoggo.app.model.data.Post
import com.instadoggo.app.model.data.Profile

class Profile_Posts_Adapter (private val profile_id: Profile?, private val current_view: Context, private val postList:ArrayList<Post>) :
    RecyclerView.Adapter<Profile_Posts_Adapter.ProfilePostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilePostViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.post_view_item, parent, false)

        return ProfilePostViewHolder(itemView)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProfilePostViewHolder, position: Int) {
        val current_post = postList[position]
        val profile_id_reference = current_post.profile_id.toString()
        holder.name_view.text = "${profile_id?.f_name} ${profile_id?.l_name}"
        holder.dog_name_view.text = profile_id?.dog_name
        Glide.with(holder.main_post_image)
            .load(current_post.post_image?.let { Database.get_image(current_view ,it, "post_images") })
            .into(holder.main_post_image)

        if (profile_id != null) { // checks if profile is null before getting the profile image
            Glide.with(holder.profile_post_image)
                .load(profile_id.profile_image?.let { Database.get_image(current_view, it, "profile_images") })
                .into(holder.profile_post_image)
        }
        holder.post_title.text = current_post.post_title
        holder.post_description.text = current_post.description
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    class ProfilePostViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val name_view : TextView = itemView.findViewById(R.id.profile_search_item_name)
        val dog_name_view : TextView = itemView.findViewById(R.id.profile_search_item_dog_name)
        val main_post_image : ImageView = itemView.findViewById(R.id.post_view_item_image)
        val profile_post_image : ImageView = itemView.findViewById(R.id.profile_search_item_profile_picture)
        val post_title : TextView = itemView.findViewById(R.id.post_view_item_title)
        val post_description : TextView = itemView.findViewById(R.id.post_view_item_description)
    }
}