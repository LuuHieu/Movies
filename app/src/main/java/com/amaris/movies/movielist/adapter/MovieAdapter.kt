package com.amaris.movies.movielist.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.amaris.movies.movielist.UiModel

class MovieAdapter : PagingDataAdapter<UiModel, MovieViewHolder>(MOVIE_DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            when (it) {
                is UiModel.MovieItem -> holder.bind(it.movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder.create(parent)

    companion object {
        private val MOVIE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<UiModel>() {
            override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
                return (oldItem is UiModel.MovieItem && newItem is UiModel.MovieItem) && oldItem.movie.imdbId == newItem.movie.imdbId
            }

            override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}