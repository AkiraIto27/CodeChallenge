package com.example.akiraito.codechallenge.weget.recycler.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.akiraito.codechallenge.databinding.ItemMovieHorizontalLayoutBinding
import com.example.akiraito.codechallenge.domain.model.Movie
import com.example.akiraito.codechallenge.extention.inflater
import com.example.akiraito.codechallenge.weget.recycler.viewholder.HorizontalMovieVH

class RecommendMovieAdapter(
    private val onMovieClick: (Movie) -> Unit,
    private val movieList: List<Movie> = listOf()
) : RecyclerView.Adapter<HorizontalMovieVH>() {

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalMovieVH {
        return HorizontalMovieVH(ItemMovieHorizontalLayoutBinding.inflate(parent.context.inflater(), parent, false))
    }

    override fun onBindViewHolder(holder: HorizontalMovieVH, position: Int) {
        holder.bind(movieList[position], onMovieClick)
    }
}