package space.iqbalsyafiq.movieapp.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import org.koin.androidx.viewmodel.ext.android.viewModel
import space.iqbalsyafiq.movieapp.core.ui.MovieAdapter
import space.iqbalsyafiq.movieapp.databinding.FragmentFavoriteBinding

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
        adapter = MovieAdapter()
        adapter.onItemClick = { movie ->
            val action = FavoriteFragmentDirections.navigateToDetailFragmentFromFavorite(movie)
            Navigation.findNavController(binding.root).navigate(action)
        }
        binding.ivBack.setOnClickListener { requireActivity().onBackPressed() }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.favoriteMovie.observe(viewLifecycleOwner) {
            it?.let { movies ->
                adapter.setData(movies)
                binding.tvEmptyList.visibility = if (movies.isEmpty()) View.VISIBLE else View.GONE

                // set adapter of rvListMovie
                binding.rvListMovie.apply {
                    setHasFixedSize(true)
                    adapter = this@FavoriteFragment.adapter
                }
            }
        }
    }
}