package com.example.ainkino

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MoviesAdapter(
    private val movies: List<Movie>?,
    val favoriteMoviesIds: ArrayList<Int>,
    val mainActivity: MainActivity,
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies?.get(position)
        holder.movieTitle.text = movie?.nameRu
        holder.movieYear.text = movie?.year.toString()
        Glide.with(holder.itemView.context).load(movie?.posterUrl).into(holder.imageView)

        holder.imageView.setOnClickListener {
            val intent = Intent(mainActivity, MovieDetailActivity::class.java)
            intent.putExtra("movieId", movie?.filmId)
            mainActivity.startActivity(intent)
        }

        val isFavorite = favoriteMoviesIds.contains(movie?.filmId)

        if (isFavorite) {
            holder.starIcon.setImageResource(R.drawable.ic_star_blue)
        } else {
            holder.starIcon.setImageResource(R.drawable.ic_star_gray)
        }

        holder.starIcon.setOnClickListener {
            val favoriteMovie =
                movie?.let { movieOne ->
                    FavoriteMovie(
                        movieOne.filmId,
                        movieOne.nameRu!!,
                        movieOne.year,
                        movieOne.posterUrl!!
                    )
                }
            if (isFavorite) {
                if (favoriteMovie != null) {
                    favoriteMoviesIds.removeAll { it == favoriteMovie.id }
                    notifyDataSetChanged()
                }
            } else {
                if (favoriteMovie != null) {
                    favoriteMoviesIds.add(favoriteMovie.id);
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return movies?.size ?: 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.moviePoster)
        val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)
        val movieYear: TextView = itemView.findViewById(R.id.movieYear)
        val starIcon = itemView.findViewById<ImageView>(R.id.starIcon)
    }
}
