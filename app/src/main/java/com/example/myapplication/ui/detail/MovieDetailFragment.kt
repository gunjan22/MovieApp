package com.example.myapplication.ui.detail

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.ConstValue
import com.example.myapplication.ConstValue.MOVIE_ID
import com.example.myapplication.util.showToast
import com.bumptech.glide.Glide
import com.example.myapplication.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.detail_fragment.*


@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.detail_fragment) {

    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arguments?.getLong(MOVIE_ID) ?: 0

        val rotate = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_inf)
        img_poster.startAnimation(rotate)

        if (movieId == 0L)
            errorOperation()

        viewModel.getDetail(movieId).observe(viewLifecycleOwner, {
            img_poster.clearAnimation()

            Glide.with(this).load(ConstValue.IMAGE_URL + it.posterPath)
                .into(img_poster)

            movie_content.visibility = View.VISIBLE

            tv_movie_name.text = it.originalTitle

            tv_language.text = it.originalLanguage

            tv_overview.text = it.overview

            tv_status.text = it.status

            progress_vote.progress = it.voteAverage.toInt()

            tv_progress_text.text=it.voteAverage.toString()
        })

        viewModel.error.observe(viewLifecycleOwner, {
            if (it)
                errorOperation()

        })

    }

    // show toast and back to previous fragment
    private fun errorOperation() {
        requireContext().showToast(getString(R.string.error_occurred))
        activity?.onBackPressed()
    }
}