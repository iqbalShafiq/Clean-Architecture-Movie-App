package space.iqbalsyafiq.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import space.iqbalsyafiq.core.databinding.ItemMovieBinding
import space.iqbalsyafiq.core.domain.model.Movie

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var _binding: ItemMovieBinding? = null
    private val binding get() = _binding!!
    private var listData = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    fun setData(newListData: List<Movie>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        listData.forEachIndexed { index, _ -> notifyItemChanged(index) }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMovieBinding.bind(itemView)

        init {
            binding.clMovie.setOnClickListener { onItemClick?.invoke(listData[adapterPosition]) }
        }

        fun bind(data: Movie) {
            with(binding) {
                Glide.with(itemView.context)
                    .asBitmap()
                    .load(IMAGE_BASE_URL + data.posterPath)
                    .transform(FitCenter(), RoundedCorners(16))
                    .into(ivMoviePoster)

                tvMovieTitle.text = data.title
                tvReleaseDate.text = data.releaseDate
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = listData[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = listData.size

    companion object {
        private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }
}