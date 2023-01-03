package com.amaa.write.ui.userDetails

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.amaa.write.R
import com.amaa.write.database.posts.PostsEntity
import com.amaa.write.databinding.ListItemBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class MyRecycleViewAdapter(private val PostsList :List<PostsEntity>):RecyclerView.Adapter<MyviewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item,parent,false)


        binding.searchButton.setOnClickListener {

            Toast.makeText(parent.context, "Share Button Clicked", Toast.LENGTH_SHORT).show()


        }


        return MyviewHolder(binding)
    }

    override fun getItemCount(): Int {
        return PostsList.size
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        holder.bind(PostsList[position])

    }


}

class MyviewHolder(private val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){

    fun bind(post : PostsEntity){
        binding.FirstNameTextView.text = post.firstName
        binding.secondNameTextView.text = post.lastName
        binding.userTextField.text = post.Posts


    }



}