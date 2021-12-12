package com.eslam.astroidradar.imagedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.fragment.navArgs
import com.eslam.astroidradar.R
import com.eslam.astroidradar.databinding.FragmentImageDetailsBinding
import com.squareup.picasso.Picasso


class ImageDetailsFragment : Fragment() {

lateinit var binding:FragmentImageDetailsBinding
private val args :ImageDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentImageDetailsBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image = args.picOfDay
        binding.lifecycleOwner = this

       if(image != null) {
          binding.imageMainTitle.text = image.title
           binding.imageDetails.text = image.explanation
           image.url?.let {
               val imgUri = it.toUri().buildUpon().scheme("https").build()
               Picasso.with(binding.imageOfDay.context)
                   .load(imgUri)
                   .placeholder(R.drawable.loading_animation)
                   .error(R.drawable.ic_baseline_broken_image_24)
                   .into(binding.imageOfDay)
           }
       }else{
           Toast.makeText(this.requireContext(),"No Image To Show",Toast.LENGTH_LONG).show()
       }


    }


}