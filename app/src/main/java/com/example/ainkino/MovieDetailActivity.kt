package com.example.ainkino

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.ImageViewCompat
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // Get movie ID from intent
        val movieId = intent.getIntExtra("movieId", -1)

        // Make network request to fetch movie details
        fetchMovieDetail(movieId)
    }

    private fun fetchMovieDetail(movieId: Int) {
        val movieService = MovieService.create()
        val call = movieService.getMovieDetail(movieId)
        val movieTitleTextView = findViewById<TextView>(R.id.movieTitle)
        val movieDescriptionTextView = findViewById<TextView>(R.id.movieDescription)
        val movieGenreTextView = findViewById<TextView>(R.id.movieGenre)
        val movieCountryTextView = findViewById<TextView>(R.id.movieCountry)
        val moviePosterImageView = findViewById<ImageView>(R.id.moviePoster)
        call.enqueue(object : Callback<MovieDetail> {
            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                if (response.isSuccessful) {
                    val movieDetail = response.body()
                    movieTitleTextView.text = movieDetail?.nameRu
                    movieDescriptionTextView.text = movieDetail?.description
                    movieGenreTextView.text = movieDetail?.genres.toString()
                    movieCountryTextView.text = movieDetail?.countries.toString()
                    Glide.with(applicationContext).load(movieDetail?.posterUrl).into(moviePosterImageView)
                }
            }

            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
            }
        })
    }

}
