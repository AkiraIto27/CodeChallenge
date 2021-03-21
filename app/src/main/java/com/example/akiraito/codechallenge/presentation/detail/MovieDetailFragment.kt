package com.example.akiraito.codechallenge.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.akiraito.codechallenge.MainActivity
import com.example.akiraito.codechallenge.databinding.FragmentMovieDetailBinding
import com.example.akiraito.codechallenge.extention.setOnSingleClickListener
import com.example.akiraito.codechallenge.utility.DialogUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModel()
    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false).also {
            it.vm = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }

        setTitle(args.title)
        setSubscribeNWError()
        setSubscribeShouldShowShareView()
        setSubscribeLikeButton()
        viewModel.fetchMovieDetail(args.movieId)

        return binding.root
    }

    /**
     * [MovieDetailViewModel.shouldDisplayNWError]を監視する
     */
    private fun setSubscribeNWError() {
        val alertDialog = DialogUtils.showErrorDialog(requireContext())
        viewModel.shouldDisplayNWError.observe(viewLifecycleOwner, Observer {
            // NWエラーダイアログが既に表示されている場合は表示しない
            if (!alertDialog.isShowing) {
                alertDialog.show()
            }
        })
    }

    /**
     * 動的にtoolBarのタイトルを変更
     */
    private fun setTitle(title: String) {
        activity ?: return
        if (activity is MainActivity) {
            (activity as MainActivity).supportActionBar?.title = title
            (activity as MainActivity).tool_bar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    /**
     * [MovieDetailViewModel.shouldShowShareView]を監視する
     */
    private fun setSubscribeShouldShowShareView() {
        viewModel.shouldShowShareView.observe(viewLifecycleOwner, Observer { url ->
            ShareCompat.IntentBuilder.from(requireActivity()).also {
                it.setHtmlText(url)
                it.setType("text/plain")
                it.startChooser()
            }
        })
    }

    /**
     * [MovieDetailViewModel.likeButton]を監視する
     */
    private fun setSubscribeLikeButton() {
        viewModel.likeButton.observe(viewLifecycleOwner, Observer { url ->
            // いいね
            binding.likeButton.also {
                it.cancelAnimation()
                it.progress = if (viewModel.isCheckFavoriteMovieIdPreferences()) 0f else 1f
                it.setOnSingleClickListener {
                    if (viewModel.isCheckFavoriteMovieIdPreferences()) {
                        it.playAnimation()
                        it.speed = 2f
                        it.progress = 0f
                        viewModel.setFavoritePreferences()
                    } else {
                        it.playAnimation()
                        it.progress = 1f
                        it.speed = -2f
                        viewModel.removeFavoritePreferences()
                    }
                }
            }
        })
    }
}