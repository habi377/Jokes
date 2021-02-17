package com.habib.jokes.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.habib.jokes.R
import com.habib.jokes.databinding.FragmentMainHomeBinding
import com.habib.jokes.network.FirebaseSource
import com.habib.jokes.ui.home.adapters.ViewPagerAdapter
import com.habib.jokes.ui.home.pagerviews.allJokes.AllOthersJokesFragment

class MainHomeFragment : Fragment() {

    private var homeViewModel :HomeViewModel? = null

    private val titles = arrayOf("Other's", "Your's", "Stared")

    private val fragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentMainHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main_home, container, false
        )
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        val homeViewModelFactory = HomeViewModelFactory(FirebaseSource.getInstance())

        fragments.add(AllOthersJokesFragment())
        fragments.add(AllOthersJokesFragment())
        fragments.add(AllOthersJokesFragment())

        binding.vpHomeMain.adapter = ViewPagerAdapter(fragments, activity as AppCompatActivity)

        TabLayoutMediator(
            binding.tlHomeMain, binding.vpHomeMain
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = titles[position]
        }.attach()

         homeViewModel =
            ViewModelProvider(
                this, homeViewModelFactory
            ).get(HomeViewModel::class.java)

        binding.homeViewModel = homeViewModel

        homeViewModel?.navigateToAuth?.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController().currentDestination?.id?.let { id ->
                    if (id == R.id.mainHomeFragment) {
                        this.findNavController().navigate(
                            MainHomeFragmentDirections.ActionMainHomeFragmentToAuthFragment()
                        )
                        homeViewModel?.doneNavigation()
                    }
                }
            }
        })
        homeViewModel?.navigateToaddDialog?.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController().currentDestination?.id?.let { id ->
                    if (id == R.id.mainHomeFragment) {
                        this.findNavController().navigate(
                            MainHomeFragmentDirections.actionMainHomeFragmentToAddJokeDialogFragment()
                        )
//                        homeViewModel?.doneNavigation()
                    }
                }
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.over_flow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
//            R.id.add_joke_menu->MarsApiFilters.SHOW_BUY
            R.id.sign_out_menu -> homeViewModel?.onLogout()
            else ->""
        }
        return true
    }
}