package com.habib.jokes.ui.home.addJoke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.habib.jokes.R
import com.habib.jokes.databinding.FragmentAddJokeDialogBinding
import com.habib.jokes.databinding.FragmentMainHomeBinding
import com.habib.jokes.models.Joke
import com.habib.jokes.network.FirebaseSource
import com.habib.jokes.ui.home.HomeViewModel
import com.habib.jokes.ui.home.HomeViewModelFactory
import kotlinx.coroutines.coroutineScope


class AddJokeDialogFragment : DialogFragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAddJokeDialogBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_joke_dialog, container, false
        )

        val jokeViewModelFactory = JokeViewModelFactory(FirebaseSource.getInstance())

        val jokeViewModel =
            ViewModelProvider(
                this,jokeViewModelFactory).get(JokeDialogViewModel::class.java)

        binding.jokeViewModel = jokeViewModel

        jokeViewModel.navigateToSubmit.observe(viewLifecycleOwner,{
            if (it==true){
                dismiss()
                jokeViewModel.doneNavigation()
            }
        })

        jokeViewModel.navigateToCancel.observe(viewLifecycleOwner,{
            if (it==true){
                dismiss()
                jokeViewModel.doneNavigation()
            }
        })


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

}