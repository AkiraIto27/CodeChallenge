package com.example.akiraito.codechallenge.weget.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.akiraito.codechallenge.BuildConfig
import com.example.akiraito.codechallenge.databinding.ItemMovieHorizontalLayoutBinding
import com.example.akiraito.codechallenge.domain.model.Movie
import com.example.akiraito.codechallenge.extention.loadImage
import com.example.akiraito.codechallenge.extention.setOnSingleClickListener

class HorizontalMovieVH(
    val binding: ItemMovieHorizontalLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: Movie,
        onMovieClick: (Movie) -> Unit
    ) {
        binding.title = item.title
        binding.imageMoviePackage.loadImage(BuildConfig.IMAGE_BACE_URL + item.backdropPath)
        binding.layoutHorizontalMovie.setOnSingleClickListener { onMovieClick.invoke(item) }
    }
}