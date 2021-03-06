package com.example.astroidradar

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.astroidradar.adapters.AsteroidListAdapter
import com.example.astroidradar.domain.Asteroid
import com.squareup.picasso.Picasso

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}
@BindingAdapter("showImage")
fun showImage(image:ImageView,imgUrl:String?)
{
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Picasso.with(image.context)
                .load(imgUri)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(image)
    }
}

@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}


@BindingAdapter("loadData")
fun loadList(recyclerView: RecyclerView, list: List<Asteroid>?)
{
    val adapter:AsteroidListAdapter = recyclerView.adapter as AsteroidListAdapter
//    if (adapter.currentList.isNotEmpty())
//    {
//        adapter.currentList.clear()
//    }

    adapter.submitList(list)

    //Log.e(null, "loadlist: ${list!!.size} ", )

}



