package com.example.akiraito.codechallenge.weget.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.akiraito.codechallenge.BuildConfig
import com.example.akiraito.codechallenge.databinding.ItemMovieGridLayoutBinding
import com.example.akiraito.codechallenge.domain.model.Movie
import com.example.akiraito.codechallenge.extention.loadImage
import com.example.akiraito.codechallenge.extention.setOnSingleClickListener

class GridMovieVH(
    val binding: ItemMovieGridLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: Movie,
        onMovieClick: (Movie) -> Unit
    ) {
        binding.imageMoviePackage.loadImage(BuildConfig.IMAGE_BACE_URL + item.posterPath)
        binding.layoutRecommendMovie.setOnSingleClickListener { onMovieClick.invoke(item) }
    }
}