package com.example.slot7

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter
    private var isGrid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerView = RecyclerView(this)
        setContentView(recyclerView)

        val movies = listOf(
            Movie(
                "Inception",
                2010,
                URL("https://m.media-amazon.com/images/I/51nbVEuw1HL._AC_SY679_.jpg")
            ),
            Movie(
                "Interstellar",
                2014,
                URL("https://m.media-amazon.com/images/I/91kFYg4fX3L._AC_SY679_.jpg")
            ),
            Movie(
                "The Dark Knight",
                2008,
                URL("https://m.media-amazon.com/images/I/51O3XkOY-8L.jpg")
            ),
            Movie("Avatar", 2009, URL("https://m.media-amazon.com/images/I/41vuKz7X9CL.jpg")),
            Movie(
                "Titanic",
                1997,
                URL("https://m.media-amazon.com/images/I/71rNJQ2g-EL._AC_SY679_.jpg")
            ),
            Movie("The Matrix", 1999, URL("https://m.media-amazon.com/images/I/51vpnbwFHrL.jpg")),
            Movie(
                "Gladiator",
                2000,
                URL("https://m.media-amazon.com/images/I/81d8ZlNUC5L._AC_SY679_.jpg")
            ),
            Movie(
                "Avengers: Endgame",
                2019,
                URL("https://m.media-amazon.com/images/I/81ExhpBEbHL._AC_SY679_.jpg")
            ),
            Movie(
                "Joker",
                2019,
                URL("https://m.media-amazon.com/images/I/71Kk9LB0A0L._AC_SY679_.jpg")
            ),
            Movie(
                "Shutter Island",
                2010,
                URL("https://m.media-amazon.com/images/I/91wC5a7TxHL._AC_SY679_.jpg")
            )
        )

        adapter = MovieAdapter(this, movies)
        recyclerView.adapter = adapter

        // set layout default
        recyclerView.layoutManager = GridLayoutManager(this, getSpanCount(isGrid))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_list -> {
                isGrid = false
                recyclerView.layoutManager = GridLayoutManager(this, getSpanCount(isGrid))
                true
            }
            R.id.menu_grid -> {
                isGrid = true
                recyclerView.layoutManager = GridLayoutManager(this, getSpanCount(isGrid))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getSpanCount(isGrid: Boolean): Int {
        val screenLayout =
            resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
        val isTablet = (screenLayout == Configuration.SCREENLAYOUT_SIZE_LARGE
                || screenLayout == Configuration.SCREENLAYOUT_SIZE_XLARGE)

        return if (isTablet) {
            if (isGrid) resources.getInteger(R.integer.span_count_grid_tablet)
            else resources.getInteger(R.integer.span_count_list_tablet)
        } else {
            if (isGrid) resources.getInteger(R.integer.span_count_grid_phone)
            else resources.getInteger(R.integer.span_count_list_phone)
        }
    }
}