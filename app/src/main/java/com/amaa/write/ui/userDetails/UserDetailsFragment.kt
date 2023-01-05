package com.amaa.write.ui.userDetails

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.amaa.write.R
import com.amaa.write.database.AppPostsDatabase
import com.amaa.write.database.posts.PostsRepository
import com.amaa.write.databinding.UserDetailsFragmentBinding

class UserDetailsFragment : Fragment() {



    private lateinit var userDetailsViewModel: UserDetailsViewModel


    private var _binding: UserDetailsFragmentBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.user_details_fragment, container, false
        )
        val application = requireNotNull(this.activity).application

        val dao = AppPostsDatabase.getInstance(application).postsDatabaseDao

        val repository = PostsRepository(dao)

        val factory = UserDetalisFactory(repository, application)

        userDetailsViewModel =
            ViewModelProvider(this, factory).get(UserDetailsViewModel::class.java)

        binding.userDelailsLayout = userDetailsViewModel

        binding.lifecycleOwner = this

        userDetailsViewModel.navigateto.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                val action = UserDetailsFragmentDirections.actionUserDetailsFragmentToLoginFragment()
                NavHostFragment.findNavController(this).navigate(action)
                userDetailsViewModel.doneNavigating()
            }
        })

        initRecyclerView()

        return binding.root

    }


    private fun initRecyclerView() {
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(this.context)
        displayUsersList()
    }


    private fun displayUsersList() {
        Log.i("MYTAG", "Inside ...UserDetails..Fragment")
        userDetailsViewModel.users.observe(viewLifecycleOwner, Observer {
            binding.usersRecyclerView.adapter = MyRecycleViewAdapter(it,userDetailsViewModel,context)
        })

    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        binding.newpostButton.setOnClickListener{

            val action = UserDetailsFragmentDirections.actionUserDetailsFragmentToPostFragment()
            NavHostFragment.findNavController(this).navigate(action)

 }



    }

}