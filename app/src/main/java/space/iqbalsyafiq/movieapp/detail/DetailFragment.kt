package space.iqbalsyafiq.movieapp.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import space.iqbalsyafiq.movieapp.R
import space.iqbalsyafiq.movieapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModel()
    private val args: DetailFragmentArgs by navArgs()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = args.movie

        with(binding) {
            ivBack.setOnClickListener { requireActivity().onBackPressed() }
            Log.d(TAG, "onViewCreated: $IMAGE_BASE_URL+${movie.backdropPath}")

            // set back movie src
            Glide.with(requireContext())
                .asBitmap()
                .load(IMAGE_BASE_URL + movie.backdropPath)
                .into(ivBackMoviePoster)

            // set poster movie src
            Glide.with(requireContext())
                .asBitmap()
                .load(IMAGE_BASE_URL + movie.posterPath)
                .transform(FitCenter(), RoundedCorners(16))
                .into(ivMoviePoster)

            // set favorite state
            setFavoriteState(movie.isFavorite)

            // set favorite state on click
            ivFavoriteMovie.setOnClickListener {
                viewModel.setFavoriteTourism(movie, !movie.isFavorite)
                setFavoriteState(!movie.isFavorite)
            }

            // set text content
            tvMovieTitle.text = movie.title
            tvMovieOverviews.text = movie.overview
            tvMoviePopularity.text = movie.popularity.toString()
            tvMovieReleaseDate.text = movie.releaseDate
        }
    }

    private fun setFavoriteState(isFavorite: Boolean) {
        if (isFavorite) binding.ivFavoriteMovie.setImageResource(R.drawable.ic_bookmarked)
        else binding.ivFavoriteMovie.setImageResource(R.drawable.ic_bookmark)
    }

    companion object {
        private val TAG = DetailFragment::class.java.simpleName
        private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }
}