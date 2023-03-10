package com.amaa.write.ui.post

import android.R.attr.defaultValue
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.amaa.write.database.AppPostsDatabase
import com.amaa.write.database.posts.PostsRepository
import com.amaa.write.database.userinformation.RegisterRepository
import com.amaa.write.databinding.FragmentPostBinding
import com.amaa.write.ui.login.LoginViewModel
import com.amaa.write.ui.userDetails.UserDetailsFragment


class PostFragment : Fragment() {

    companion object {
        val USERNAME = "username"
        val FIRSTNAME = "firstname"
        val LASTNAME = "lastname"
    }

    private lateinit var usernameId: String
    private lateinit var firstnameId: String
    private lateinit var lastnameId: String

    private var _binding: FragmentPostBinding  ? = null
    private val binding get() = _binding!!
    private lateinit var postsViewModel: PostFragmentViewModel
    private lateinit var username: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            usernameId = it.getString(UserDetailsFragment.USERNAME).toString()
            firstnameId = it.getString(UserDetailsFragment.FIRSTNAME).toString()
            lastnameId = it.getString(UserDetailsFragment.LASTNAME).toString()
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        val dao = AppPostsDatabase.getInstance(application).postsDatabaseDao

        val repository = PostsRepository(dao)

        val factory = PostViewModelFactory(repository, application)
   //============================================================================================



        postsViewModel = ViewModelProvider(this, factory).get(PostFragmentViewModel::class.java)

        binding.postsLayout = postsViewModel

        binding.lifecycleOwner = this



        postsViewModel.navigateto.observe(viewLifecycleOwner, Observer { hasFinished->
            if (hasFinished == true){
                displayPostsList()
                postsViewModel.doneNavigating()
            }
        })

        postsViewModel.userPostsLiveData.observe(viewLifecycleOwner, Observer {
        })


        postsViewModel.errotoast.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "You forgot to write the post :) ", Toast.LENGTH_SHORT).show()
                postsViewModel.donetoast()
            }
        })

        postsViewModel.errotoastUsername.observe(viewLifecycleOwner, Observer {})


 //========================================================================================================

        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)


        _binding?.cancelButton?.setOnClickListener{

            val action = PostFragmentDirections.actionPostFragmentToUserDetailsFragment(usernameId,firstnameId,lastnameId)
            NavHostFragment.findNavController(this).navigate(action)
        }


        //passing data from login fragment to post fragment
        postsViewModel.email = usernameId
        postsViewModel.firstName = firstnameId
        postsViewModel.lastName = lastnameId

    }


    private fun displayPostsList() {
        val action = PostFragmentDirections.actionPostFragmentToUserDetailsFragment(usernameId,firstnameId,lastnameId)
        NavHostFragment.findNavController(this).navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }


}