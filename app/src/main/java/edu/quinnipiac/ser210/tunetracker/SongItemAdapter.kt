package edu.quinnipiac.ser210.tunetracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.NavController
// Assume `Result` is your data model class for each song
// Replace `Result` with your actual data model class name

class SongItemAdapter(val context: Context, var navController: NavController) : RecyclerView.Adapter<SongItemAdapter.SongItemViewHolder>() {

    var data = listOf<Result>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    fun setSearchListItems(searchData: List<Result>) {
        data = searchData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongItemViewHolder =
        SongItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: SongItemViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    class SongItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            fun inflateFrom(parent: ViewGroup): SongItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_song, parent, false)
                return SongItemViewHolder(view)
            }
        }

        fun bind(item: Result) {
            // val image: ImageView = view.findViewById(R.id.songImage)
            // val songName: TextView = view.findViewById(R.id.songName)
            // val artist: TextView = view.findViewById(R.id.artist)

            // songName.text = item.title
            // artist.text = "${item.artist} ${item.duration}"
        }
    }
}
