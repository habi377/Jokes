package com.habib.jokes.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.habib.jokes.R
import com.habib.jokes.databinding.JokesListBinding
import com.habib.jokes.models.Joke

class AllOthersJokesAdapter() : androidx.recyclerview.widget.ListAdapter<Joke,AllOthersJokesAdapter.ViewHolder>(JokesDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(val binding: JokesListBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Joke) {
            binding.joke = item
            binding.tvMailList.text = item.email
            binding.tvJokeList.text = item.joke
            if (item.downVote)
                binding.btnDisLikeList.setImageResource(R.drawable.ic_dis_like_fill)
            else
                binding.btnDisLikeList.setImageResource(R.drawable.ic_dis_like_outline)
            if (item.upVote)
                binding.btnLikeList.setImageResource(R.drawable.ic_like_fill)
            else
                binding.btnLikeList.setImageResource(R.drawable.ic_like_outline)
            if (item.stared)
                binding.btnFavList.setImageResource(R.drawable.ic_star_fill)
            else
                binding.btnFavList.setImageResource(R.drawable.ic_star_outline)
        //            binding.executePendingBindings()
//            binding.clickListener = sleepNightListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = JokesListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class JokesDiffCallback : DiffUtil.ItemCallback<Joke>(){
    override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
        return oldItem.userId== newItem.userId
    }

    override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
        return oldItem == newItem
    }

}