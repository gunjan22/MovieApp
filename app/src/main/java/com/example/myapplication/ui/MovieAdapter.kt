package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ConstValue.IMAGE_URL
import com.example.myapplication.data.model.MovieModel
import com.bumptech.glide.Glide
import com.example.myapplication.R
import kotlinx.android.synthetic.main.movie_item.view.*
import javax.inject.Inject


class MovieAdapter @Inject constructor(private val listener: ClickListener) :
    PagingDataAdapter<MovieModel, MovieAdapter.ViewHolder>(MovieDiff) {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.clicked(getItem(position)?.id)
        }
        Glide.with(holder.itemView).load(IMAGE_URL + getItem(position)?.posterPath)
            .into(holder.itemView.movie_poster)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_item, parent, false)
        )
    }

    object MovieDiff : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
            newItem == oldItem
    }

    fun withMyFooter(
        footer: LoadStateAdapter<*>
    ): ConcatAdapter {
        addLoadStateListener { loadStates ->
            footer.loadState = when (loadStates.refresh) {
                is LoadState.NotLoading -> loadStates.append
                else -> loadStates.refresh
            }
        }
        return ConcatAdapter(this, footer)
    }

}