package com.example.myapplication.ui

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.ConstValue.MOVIE_ID
import com.example.myapplication.R
import com.example.myapplication.data.model.Movie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_fragment.*
import kotlinx.android.synthetic.main.top_sidebar_layout.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.movie_fragment), ClickListener {

    companion object {
        private const val MOTION_TRANSITION_COMPLETED = 1F
        private const val MOTION_TRANSITION_INITIAL = 0F
    }

    // detect if scrolled
    private var hasMotionScrolled = false

    var movieAdapter: MovieAdapter = MovieAdapter(this)

    private val viewModel: MainViewModel by viewModels()

    private lateinit var rotate: Animation


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObj()

        initRecyclerView()

        gettingData()

        setupCarasoul()

        manageClick()

    }

    private fun manageClick() {
        search_bar.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)

        }

        hbg_menu.setOnClickListener {
            Toast.makeText(requireContext(),"Not Implement", Toast.LENGTH_SHORT).show()
        }


    }

    private fun setupCarasoul() {
        val movies = arrayListOf<Movie>()

        val posters = resources.obtainTypedArray(R.array.posters)

        for (i in resources.getStringArray(R.array.titles).indices) {
            movies.add(
                Movie(
                    posters.getResourceId(i, -1),
                    resources.getStringArray(R.array.titles)[i],
                    resources.getStringArray(R.array.overviews)[i]
                )
            )
        }

        posters.recycle()

        viewPager.adapter = MovieCarosoulAdapter(movies)
    }

    //initialize object
    private fun initObj() {
        rotate = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)

    }


    // getting data from net
    private fun gettingData() {
        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->

                movieAdapter.submitData(pagingData)
            }
        }

    }


    // initialize recyclerView
    private fun initRecyclerView() {
        recyclerView.apply {

            val mLayoutManager = GridLayoutManager(context, 3)

            // if it's loading or error just show as once
            mLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == movieAdapter.itemCount) {
                        if (position % 3 == 0)
                            3
                        else
                            1
                    } else
                        1
                }
            }

            recyclerView.layoutManager = mLayoutManager
            adapter = movieAdapter.withMyFooter(
                footer = MovieLoadStateAdapter { movieAdapter.retry() }
            )
        }
    }

    override fun clicked(value: Long?) {

        val movieId = Bundle()
        movieId.putLong(MOVIE_ID, value ?: 0)

        findNavController().navigate(R.id.action_movieFragment_to_movieDetailFragment, movieId)
    }

}