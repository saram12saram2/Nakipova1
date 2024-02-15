package com.example.ainkino

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var movies: ArrayList<Movie>
    private lateinit var recyclerView: RecyclerView
    val favoriteMoviesIds = arrayListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            // Restore the list of movies from saved instance state
            movies = savedInstanceState.getParcelableArrayList("movies")!!
            movies.let {
                showMovies(it)
            }
        } else {
            fetchPopularMovies()
        }

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("movies", movies)
    }


    private fun showMovies(movies: List<Movie>?) {
        val adapter = MoviesAdapter(movies, favoriteMoviesIds,this)
        recyclerView.adapter = adapter
    }

    private fun fetchPopularMovies() {
        val movieService = MovieService.create()
        val call = movieService.getPopularMovies("TOP_100_POPULAR_FILMS")
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                movies = (response.body()?.films as ArrayList<Movie>?)!!
                showMovies(movies)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                // Handle network error
                Toast.makeText(
                    this@MainActivity,
                    "Failed to fetch movies: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}


