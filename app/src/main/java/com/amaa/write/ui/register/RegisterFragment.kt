package com.amaa.write.ui.register


import android.os.Bundle
import android.util.Log
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
import com.amaa.write.databinding.RegisterHomeFragmentBinding



class RegisterFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: RegisterHomeFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.register_home_fragment, container, false
        )

        val application = requireNotNull(this.activity).application

        val dao = AppDatabase.getInstance(application).registerDatabaseDao

        val repository = RegisterRepository(dao)

        val factory = RegisterViewModelFactory(repository, application)

        registerViewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]

        binding.myViewModel = registerViewModel

        binding.lifecycleOwner = this
        
        registerViewModel.navigateto.observe(viewLifecycleOwner, Observer { hasFinished->
            if (hasFinished == true){
                displayUsersList()
                registerViewModel.doneNavigating()
            }
        })

        registerViewModel.userDetailsLiveData.observe(viewLifecycleOwner, Observer {
        })


        registerViewModel.errotoast.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                registerViewModel.donetoast()
            }
        })



        registerViewModel.errotoastUsername.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Email Already Exists", Toast.LENGTH_SHORT).show()
                registerViewModel.donetoastUserName()
            }
        })

        return binding.root
    }

    private fun displayUsersList() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        NavHostFragment.findNavController(this).navigate(action)

    }



}