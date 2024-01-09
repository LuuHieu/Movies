package com.amaris.movies.movielist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amaris.data.local.entities.MovieEntity
import com.amaris.movies.databinding.MovieViewholderBinding

class MovieViewHolder(
    private val binding: MovieViewholderBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: MovieEntity) {
        binding.apply {
            title.text = movie.title
            description.text = movie.year
            created.text = movie.year
        }
    }

    companion object {
        fun create(parent: ViewGroup): MovieViewHolder {
            val binding = MovieViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MovieViewHolder(binding)
        }
    }
}