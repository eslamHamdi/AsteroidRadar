package com.example.astroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.astroidradar.AsteroidRadarApplication
import com.example.astroidradar.Constants
import com.example.astroidradar.R
import com.example.astroidradar.adapters.AsteroidListAdapter
import com.example.astroidradar.databinding.FragmentMainBinding
import com.example.astroidradar.domain.Asteroid

class MainFragment : Fragment(),AsteroidListAdapter.OnItemClick {

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this,MainViewModel.Factory(activity.application as AsteroidRadarApplication)).get(MainViewModel::class.java)

    }


    val adapter = AsteroidListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        adapter.clicker = this
        binding.asteroidRecycler.adapter = adapter
        var imageTitle = ""
        viewModel.image.observe(viewLifecycleOwner){
            if (it != null) {
                imageTitle = it.title!!
            }
        }

        binding.activityMainImageOfTheDay.setOnClickListener {

            val description = imageTitle
            Toast.makeText(this.requireContext(),description,Toast.LENGTH_LONG).show()
        }



        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var listType:String? = null

            when(item.itemId )
            {

                R.id.show_all_menu-> listType = Constants.All
                R.id.show_day_menu-> listType = Constants.DAY
                R.id.show_week_menu -> listType = Constants.WEEK
                else -> viewModel.deleteAll()
            }

        Log.e(null, "onOptionsItemSelected: $listType ", )
        listType.let {
            if (it != null) {
                viewModel.getData(it)
            }
        }

        return super.onOptionsItemSelected(item)

    }

    override fun onClick(item: Asteroid) {
        findNavController().navigate(MainFragmentDirections.actionShowDetail(item))
    }
}
