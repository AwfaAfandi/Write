package com.amaa.write.ui.updateprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.amaa.write.R
import com.amaa.write.database.AppDatabase
import com.amaa.write.database.userinformation.RegisterRepository
import com.amaa.write.databinding.FragmentUpdateProfileBinding
import com.amaa.write.ui.userDetails.UserDetailsFragment

class UpdateProfileFragment : Fragment() {

    private lateinit var updateProfileViewModel: UpdateProfileViewModel


    companion object {
        val USERNAME = "username"
        val FIRSTNAME = "firstname"
        val LASTNAME = "lastname"
    }

    private lateinit var usernameId: String
    private lateinit var firstnameId: String
    private lateinit var lastnameId: String


    private var _binding: FragmentUpdateProfileBinding? = null
    private val binding get() = _binding!!


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
         _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_update_profile, container, false
        )

        val application = requireNotNull(this.activity).application

        val dao = AppDatabase.getInstance(application).registerDatabaseDao

        val repository = RegisterRepository(dao)

        val factory = UpdateProfileViewModelFactory(repository, application)

        updateProfileViewModel = ViewModelProvider(this, factory)[UpdateProfileViewModel::class.java]

        binding.updateProfile = updateProfileViewModel

        binding.lifecycleOwner = this

        updateProfileViewModel.navigateto.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                displayLogIn()
                updateProfileViewModel.doneNavigating()
            }
        })

        updateProfileViewModel.userDetailsLiveData.observe(viewLifecycleOwner, Observer {
        })


        updateProfileViewModel.errotoast.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
                updateProfileViewModel.donetoast()
            }
        })

        updateProfileViewModel.errotoastUsername.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "Email Not Exists", Toast.LENGTH_SHORT).show()
                updateProfileViewModel.donetoastUserName()
            }
        })

        return binding.root
    }

    private fun displayLogIn() {
        val action =
            UpdateProfileFragmentDirections.actionUpdateProfileFragmentToLoginFragment()
        NavHostFragment.findNavController(this).navigate(action)

    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateProfileViewModel.inputUsername = usernameId

        binding.bottomBar.setOnItemSelectedListener {
            it.itemId

            when (it.itemId) {

                R.id.Home_tab -> {
                    val action =
                        UpdateProfileFragmentDirections.actionUpdateProfileFragmentToUserDetailsFragment(
                            usernameId,
                            firstnameId,
                            lastnameId
                        )
                    NavHostFragment.findNavController(this).navigate(action)

                    return@setOnItemSelectedListener true
                }

                R.id.profile_tab -> {

                    return@setOnItemSelectedListener true
                }
                else -> {

                    val action =
                        UpdateProfileFragmentDirections.actionUpdateProfileFragmentToLoginFragment()
                    NavHostFragment.findNavController(this).navigate(action)

                    return@setOnItemSelectedListener true
                }
            }


        }


    }

}