package com.example.akiraito.codechallenge.weget.recycler.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.akiraito.codechallenge.databinding.ItemRecommendMovieLayoutBinding
import com.example.akiraito.codechallenge.domain.model.Movie
import com.example.akiraito.codechallenge.extention.inflater
import com.example.akiraito.codechallenge.weget.recycler.viewholder.MovieVH

class RecommendMovieRecyclerAdapter(
    private val onItemClickListener: (Movie) -> Unit
) : ListAdapter<Movie, MovieVH>(object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieVH(
        ItemRecommendMovieLayoutBinding.inflate(parent.context.inflater(), parent, false)
    )

    override fun onBindViewHolder(
        holder: MovieVH,
        position: Int
    ) {
        holder.bind(getItem(position), onItemClickListener)
    }
}