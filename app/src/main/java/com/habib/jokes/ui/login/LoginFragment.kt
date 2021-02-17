package com.habib.jokes.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.habib.jokes.R
import com.habib.jokes.databinding.FragmentLoginBinding
import com.habib.jokes.network.FirebaseSource
import com.habib.jokes.network.UserRepository
import com.habib.jokes.ui.AuthFragmentDirections


class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )

        // we can find the outer NavController passing the owning Activity
        // and the id of a view associated to that NavController,
        // for example the NavHostFragment id:
        val mainNavController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)


        val loginViewModelFactory = LoginViewModelFactory(FirebaseSource.getInstance())

        val loginViewModel =
                 ViewModelProvider(
                     this,loginViewModelFactory).get(LoginViewModel::class.java)

        binding.loginViewModel = loginViewModel

        loginViewModel.navigateToSignUp.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController().navigate(
                    LoginFragmentDirections.ActionLoginFragmentToSignUpFragment())
                loginViewModel.doneNavigateToSignUp()
            }
        })

        loginViewModel.navigateToHome.observe(viewLifecycleOwner,{
            if (it==true){
                mainNavController.navigate(
                    AuthFragmentDirections.actionAuthFragmentToMainHomeFragment())
                loginViewModel.doneNavigateToHome()
            }
        })

        return binding.root
    }
}