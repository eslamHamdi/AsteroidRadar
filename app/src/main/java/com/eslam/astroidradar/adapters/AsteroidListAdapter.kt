package com.eslam.astroidradar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eslam.astroidradar.R
import com.eslam.astroidradar.databinding.AsteroidItemBinding
import com.eslam.astroidradar.domain.Asteroid

class AsteroidListAdapter:ListAdapter<Asteroid,AsteroidListAdapter.AsteroidViewHolder>(DiffCallBack)
{



   inner class AsteroidViewHolder(val binding:AsteroidItemBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun onBind(asteroid:Asteroid)
        {

                binding.asteroid = asteroid
                binding.itemContainer.setOnClickListener {
                    clicker?.onClick(asteroid)
                }
                binding.executePendingBindings()


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

        val binding = DataBindingUtil.inflate<AsteroidItemBinding>(LayoutInflater
            .from(parent.context), R.layout.asteroid_item,parent,false)

        return AsteroidViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)


    }

    var clicker:OnItemClick? = null
    interface OnItemClick
    {
        fun onClick(item:Asteroid)
    }
}