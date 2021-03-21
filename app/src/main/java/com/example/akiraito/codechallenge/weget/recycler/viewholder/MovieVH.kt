package com.example.akiraito.codechallenge.weget.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.akiraito.codechallenge.BuildConfig
import com.example.akiraito.codechallenge.databinding.ItemRecommendMovieLayoutBinding
import com.example.akiraito.codechallenge.domain.model.Movie
import com.example.akiraito.codechallenge.extention.loadImage

class MovieVH(
    private val binding: ItemRecommendMovieLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(itemData: Movie, onItemClickListener: (Movie) -> Unit) {
        binding.imageMoviePoster.loadImage(BuildConfig.IMAGE_BACE_URL + itemData.posterPath)
        binding.movie = itemData
        binding.imageMoviePoster.setOnClickListener { onItemClickListener.invoke(itemData) }
    }
}