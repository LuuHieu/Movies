package com.amaris.movies.movielist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.amaris.movies.R
import com.amaris.movies.databinding.MovieLoadStateFooterViewItemBinding

class MovieLoadStateViewHolder(
    private val binding: MovieLoadStateFooterViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root)  {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        with(binding) {
            if (loadState is LoadState.Error) {
                errorMsg.text = loadState.error.localizedMessage
            }
            progressBar.isVisible = loadState is LoadState.Loading
            retryButton.isVisible = loadState is LoadState.Error
            errorMsg.isVisible = loadState is LoadState.Error
        }
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): MovieLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_load_state_footer_view_item, parent, false)
            val binding = MovieLoadStateFooterViewItemBinding.bind(view)
            return MovieLoadStateViewHolder(binding, retry)
        }
    }
}