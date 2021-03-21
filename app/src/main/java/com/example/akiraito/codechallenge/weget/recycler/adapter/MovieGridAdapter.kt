package com.example.akiraito.codechallenge.weget.recycler.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.akiraito.codechallenge.databinding.ItemMovieGridLayoutBinding
import com.example.akiraito.codechallenge.domain.model.Movie
import com.example.akiraito.codechallenge.extention.inflater
import com.example.akiraito.codechallenge.weget.recycler.viewholder.GridMovieVH

class MovieGridAdapter(
    private val onMovieClick: (Movie) -> Unit,
    private val movieList: ArrayList<Movie> = arrayListOf()
) : RecyclerView.Adapter<GridMovieVH>() {
    fun updateMovieList(movieList: List<Movie>) {
        this.movieList.clear()
        this.movieList.addAll(movieList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridMovieVH {
        return GridMovieVH(ItemMovieGridLayoutBinding.inflate(parent.context.inflater()))
    }

    override fun onBindViewHolder(holder: GridMovieVH, position: Int) {
        holder.bind(movieList[position], onMovieClick)
    }
}