package com.example.slot7

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieAdapter(private val context: Context, private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(val layout: ConstraintLayout) : RecyclerView.ViewHolder(layout) {
        val poster: ImageView = ImageView(context)
        val title: TextView = TextView(context)
        val year: TextView = TextView(context)

        init {
            poster.id = ImageView.generateViewId()
            title.id = TextView.generateViewId()
            year.id = TextView.generateViewId()

            layout.addView(poster)
            layout.addView(title)
            layout.addView(year)

            val set = ConstraintSet()
            set.clone(layout)

            // Poster
            set.constrainWidth(poster.id, 200)
            set.constrainHeight(poster.id, 300)
            set.connect(
                poster.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 16
            )
            set.connect(
                poster.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 16
            )

            // Title
            set.constrainWidth(title.id, ConstraintSet.WRAP_CONTENT)
            set.constrainHeight(title.id, ConstraintSet.WRAP_CONTENT)
            set.connect(
                title.id, ConstraintSet.TOP, poster.id, ConstraintSet.BOTTOM, 8
            )
            set.connect(
                title.id, ConstraintSet.START, poster.id, ConstraintSet.START
            )

            // Year
            set.constrainWidth(year.id, ConstraintSet.WRAP_CONTENT)
            set.constrainHeight(year.id, ConstraintSet.WRAP_CONTENT)
            set.connect(
                year.id, ConstraintSet.TOP, title.id, ConstraintSet.BOTTOM, 4
            )
            set.connect(
                year.id, ConstraintSet.START, title.id, ConstraintSet.START
            )

            set.applyTo(layout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layout = ConstraintLayout(context)
        layout.layoutParams = LinearLayoutCompat.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return MovieViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.title.text = movie.title
        holder.year.text = movie.year.toString()

        Glide.with(context)
            .load(movie.imageURL.toString())
            .placeholder(android.R.drawable.ic_menu_report_image)
            .into(holder.poster)

        holder.layout.setOnClickListener {
            Toast.makeText(context, "Clicked: ${movie.title}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = movies.size
}