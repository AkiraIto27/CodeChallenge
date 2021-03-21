package com.example.akiraito.codechallenge.weget.recycler.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.akiraito.codechallenge.databinding.ItemHorizontalMovieListLayoutBinding
import com.example.akiraito.codechallenge.domain.model.Genres
import com.example.akiraito.codechallenge.domain.model.Movie
import com.example.akiraito.codechallenge.extention.inflater

// ConcatAdapterは横スクロールと縦スクロールの混在が不可能なため、Adapterを１つ噛ませて実装している
class HorizontalScrollMovieListAdapter(
    private val genres: Genres,
    private val onItemClickListener: (Movie) -> Unit
) : RecyclerView.Adapter<HorizontalScrollMovieListAdapter.HorizontalScrollMovieListVH>() {
    private val movieList: MutableList<Movie> = mutableListOf()

    /**
     * 映画リストの更新
     */
    fun updateMovieList(movieList: List<Movie>) {
        this.movieList.clear()
        this.movieList.addAll(movieList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalScrollMovieListVH {
        return HorizontalScrollMovieListVH(
            ItemHorizontalMovieListLayoutBinding.inflate(parent.context.inflater(), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HorizontalScrollMovieListVH, position: Int) {
        holder.bind(genres, movieList, onItemClickListener)
    }

    override fun getItemCount() = 1

    inner class HorizontalScrollMovieListVH(
        val binding: ItemHorizontalMovieListLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private var adapter: RecommendMovieRecyclerAdapter? = null

        fun bind(
            genres: Genres,
            itemData: List<Movie>,
            onItemClickListener: (Movie) -> Unit
        ) {
            binding.title = genres.name
            adapter ?: run {
                adapter = RecommendMovieRecyclerAdapter(
                    onItemClickListener
                )
                binding.recyclerMovieList.adapter = adapter
            }
            adapter?.submitList(itemData)
        }
    }
}