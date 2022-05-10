package space.iqbalsyafiq.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import space.iqbalsyafiq.core.domain.model.Movie
import space.iqbalsyafiq.core.ui.MovieAdapter
import space.iqbalsyafiq.favorite.databinding.FragmentFavoriteBinding
import space.iqbalsyafiq.favorite.di.favoriteModule

class FavoriteFragment : Fragment() {

    private val viewModel: FavoriteViewModel by viewModel()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadKoinModules(favoriteModule)

        adapter = MovieAdapter()
        adapter.onItemClick = { movie ->
            val action = FavoriteFragmentDirections.navigateToDetailFragmentFromFavorite(
                movie
            )
            Navigation.findNavController(binding.root).navigate(action)
        }
        binding.ivBack.setOnClickListener { requireActivity().onBackPressed() }
        binding.etSearch.addTextChangedListener {
            Log.d("Search", "onViewCreated: ${it.toString()}")
            viewModel.searchFavoriteMovie(it.toString()).observe(viewLifecycleOwner) { searched ->
                searched?.let { movies ->
                    setAdapter(movies)
                }
            }
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.favoriteMovie.observe(viewLifecycleOwner) {
            it?.let { movies -> setAdapter(movies) }
        }
    }

    private fun setAdapter(movies: List<Movie>) {
        adapter.setData(movies)
        binding.tvEmptyList.visibility = if (movies.isEmpty()) View.VISIBLE else View.GONE

        // set adapter of rvListMovie
        binding.rvListMovie.apply {
            setHasFixedSize(true)
            adapter = this@FavoriteFragment.adapter
        }
    }

    override fun onPause() {
        super.onPause()
        _binding = null
    }
}