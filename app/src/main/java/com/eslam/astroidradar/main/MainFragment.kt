package com.eslam.astroidradar.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.eslam.astroidradar.Constants
import com.eslam.astroidradar.R
import com.eslam.astroidradar.adapters.AsteroidListAdapter
import com.eslam.astroidradar.data_transfer_opjects.PictureOfDay
import com.eslam.astroidradar.databinding.FragmentMainBinding
import com.eslam.astroidradar.domain.Asteroid
import com.eslam.astroidradar.isNetworkConnected

class MainFragment : Fragment(), AsteroidListAdapter.OnItemClick {

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(
            this,
            MainViewModel.Factory(activity.application as com.eslam.astroidradar.AsteroidRadarApplication)
        ).get(MainViewModel::class.java)

    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        val adapter = AsteroidListAdapter()
        adapter.clicker = this

        binding.asteroidRecycler.adapter = adapter

        var image: PictureOfDay? = null
        viewModel.image.observe(viewLifecycleOwner) {
            if (it != null) {
                image = it
            }

        }

        binding.activityMainImageOfTheDay.setOnClickListener {

            if (image != null) {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToImageDetailsFragment(
                        image
                    )
                )
            } else {
                Toast.makeText(this.requireContext(), "No Image Is Loaded", Toast.LENGTH_LONG)
                    .show()
            }
        }

        viewModel.viewData?.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty() && !isNetworkConnected(this.requireContext())) {
                Toast.makeText(
                    this.requireContext(),
                    "Please Connect To Internet And Refresh again.",
                    Toast.LENGTH_LONG
                ).show()
            } else if (it.isNullOrEmpty()) {
                binding.statusLoadingWheel.visibility = View.VISIBLE
            } else {
                adapter.submitList(it)
                binding.statusLoadingWheel.visibility = View.GONE
            }

        }



        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var listType: String? = null

        when (item.itemId) {

            R.id.show_all_menu -> listType = Constants.All
            R.id.show_day_menu -> listType = Constants.DAY
            R.id.show_week_menu -> listType = Constants.WEEK
            else -> {
                try {
                    viewModel.deleteAll()

                } catch (e: Exception) {
                    Toast.makeText(this.requireContext(), e.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        Log.e(null, "onOptionsItemSelected: $listType ")
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

//app:goneIfNotNull="@{viewModel.viewData}"