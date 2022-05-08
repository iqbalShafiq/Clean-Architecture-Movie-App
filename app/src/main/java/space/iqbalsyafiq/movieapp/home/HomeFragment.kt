package space.iqbalsyafiq.movieapp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import org.koin.androidx.viewmodel.ext.android.viewModel
import space.iqbalsyafiq.movieapp.core.data.Resource
import space.iqbalsyafiq.movieapp.core.ui.MovieAdapter
import space.iqbalsyafiq.movieapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init adapter
        adapter = MovieAdapter()
        adapter.onItemClick = { movie ->
            val action = HomeFragmentDirections.navigateToDetailFragment(movie)
            Navigation.findNavController(binding.root).navigate(action)
        }

        // on refresh
        binding.refreshLayout.setOnRefreshListener { observeLiveData() }

        // on favorite list clicked
        binding.btnFavoriteList.setOnClickListener {
            val action = HomeFragmentDirections.navigateToFavoriteFragment()
            Navigation.findNavController(binding.root).navigate(action)
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.movie.observe(viewLifecycleOwner) {
            it?.let { movie ->
                when (movie) {
                    is Resource.Loading -> binding.refreshLayout.isRefreshing = true
                    is Resource.Success -> {
                        binding.refreshLayout.isRefreshing = false
                        adapter.setData(movie.data)

                        // set adapter of rvListMovie
                        binding.rvListMovie.apply {
                            setHasFixedSize(true)
                            adapter = this@HomeFragment.adapter
                        }
                    }
                    is Resource.Error -> {
                        binding.refreshLayout.isRefreshing = false
                        binding.tvErrorApi.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}