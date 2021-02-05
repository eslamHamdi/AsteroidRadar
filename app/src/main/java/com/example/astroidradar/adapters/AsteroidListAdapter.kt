package com.example.astroidradar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.astroidradar.R
import com.example.astroidradar.databinding.AsteroidItemBinding
import com.example.astroidradar.domain.Asteroid

class AsteroidListAdapter:ListAdapter<Asteroid,AsteroidListAdapter.AsteroidViewHolder>(DiffCallBack)
{



    class AsteroidViewHolder(val binding:AsteroidItemBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun onBind(asteroid:Asteroid)
        {
            binding.asteroid = asteroid
            binding.executePendingBindings()

        }

        companion object
        {
            fun getInstance(parent: ViewGroup):AsteroidViewHolder
            {
                val binding = DataBindingUtil.inflate<AsteroidItemBinding>(LayoutInflater
                    .from(parent.context), R.layout.asteroid_item,parent,false)

                return AsteroidViewHolder(binding)
            }
        }
    }

    companion object DiffCallBack:DiffUtil.ItemCallback<Asteroid>()
    {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {

            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {

        return AsteroidViewHolder.getInstance(parent)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)


    }
}