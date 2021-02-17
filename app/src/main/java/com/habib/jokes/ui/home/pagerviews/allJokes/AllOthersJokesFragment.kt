package com.habib.jokes.ui.home.pagerviews.allJokes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.habib.jokes.R
import com.habib.jokes.databinding.FragmentAllOthersJokesBinding
import com.habib.jokes.databinding.FragmentMainHomeBinding
import com.habib.jokes.models.Joke.Companion.toJoke
import com.habib.jokes.network.FirebaseSource
import com.habib.jokes.ui.home.HomeViewModel
import com.habib.jokes.ui.home.HomeViewModelFactory
import com.habib.jokes.ui.home.adapters.AllOthersJokesAdapter

class AllOthersJokesFragment : Fragment() , EventListener<QuerySnapshot> {

    private lateinit var adapter: AllOthersJokesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentAllOthersJokesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_all_others_jokes, container, false
        )

        val viewModelFactory = AllOthersViewModelProvider(FirebaseSource.getInstance())
        adapter = AllOthersJokesAdapter()
        val viewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(AllOtherJokeViewModel::class.java)

        binding.rvOthersJokes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOthersJokes.adapter = adapter

        viewModel.othersJokes.observe(viewLifecycleOwner,{
            if (it!= null)
                adapter.submitList(it)
        })

        return binding.root
    }

    override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
        adapter.submitList(value?.documents?.mapNotNull {
            it.toJoke()
        })
        Log.d("asdsadsad",error.toString())
    }
}