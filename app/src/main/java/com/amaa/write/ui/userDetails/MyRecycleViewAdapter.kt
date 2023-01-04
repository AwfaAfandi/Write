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

class MyRecycleViewAdapter(private val PostsList: List<PostsEntity>,viewModel: UserDetailsViewModel,context: Context?):RecyclerView.Adapter<MyviewHolder>(){

    val userDetailsViewModel = viewModel
    val adapterContext = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item,parent,false)
        return MyviewHolder(binding)

    }


    override fun getItemCount(): Int {
        return PostsList.size
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {

        val item = PostsList[position]
        holder.bind(item,userDetailsViewModel,adapterContext)
    }


}

class MyviewHolder(private val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){
//    private var userDetailsViewModel: UserDetailsViewModel? = null

    fun bind(post : PostsEntity,viewModel: UserDetailsViewModel,context: Context?){
        binding.FirstNameTextView.text = post.firstName
        binding.secondNameTextView.text = post.lastName
        binding.userTextField.text = post.Posts

        val intent = Intent(Intent.ACTION_SEND).apply {
            type ="text/*"
            putExtra(Intent.EXTRA_TEXT , post.Posts)
        }

        binding.searchButton.setOnClickListener {
         binding.root.context.startActivity(intent)

        }

   binding.deleteButton.setOnClickListener {
    viewModel.deletepost(post)
}



        }


}
