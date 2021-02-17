package com.habib.jokes.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper.getMainLooper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.ktx.Firebase
import com.habib.jokes.R
import com.habib.jokes.network.FirebaseSource


/**
 * A simple [Fragment] subclass.
 * Use the [SplashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SplashFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(getMainLooper()).postDelayed({
            if (FirebaseSource.getInstance().currentUser()!=null)
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMainHomeFragment())
            else
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToAuthFragment())
        }, 2000.toLong())
    }
}