package com.amaa.write.ui.userDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.amaa.write.R
import com.amaa.write.database.posts.PostsEntity
import com.amaa.write.databinding.ListItemBinding

class MyRecycleViewAdapter(private val PostsList: List<PostsEntity>,viewModel: UserDetailsViewModel):RecyclerView.Adapter<MyviewHolder>(){

    val userDetailsViewModel = viewModel

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
        holder.bind(PostsList[position],userDetailsViewModel)


    }


}

class MyviewHolder(private val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){
//    private var userDetailsViewModel: UserDetailsViewModel? = null

    fun bind(post : PostsEntity,viewModel: UserDetailsViewModel){
        binding.FirstNameTextView.text = post.firstName
        binding.secondNameTextView.text = post.lastName
        binding.userTextField.text = post.Posts

        // val to get context
        val context = binding.root.context

        binding.searchButton.setOnClickListener {

        Toast.makeText(binding.root.context, post.Posts, Toast.LENGTH_SHORT).show()
        }

   binding.deleteButton.setOnClickListener {
    viewModel.deletepost(post)

     //  Toast.makeText(binding.root.context, post.Posts, Toast.LENGTH_SHORT).show()

   }



        }


}
