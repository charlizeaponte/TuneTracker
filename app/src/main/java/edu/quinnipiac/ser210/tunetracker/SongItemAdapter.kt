package edu.quinnipiac.ser210.tunetracker
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import edu.quinnipiac.ser210.tunetracker.api.song.Song
import androidx.navigation.fragment.findNavController
//var songs = listOf<Song>()
var songs : ArrayList<Song> = ArrayList()


class SongItemAdapter(val context: Context, var navController: NavController) : RecyclerView.Adapter<SongItemAdapter.SongItemViewHolder>() {


    //current error- Song object is not defined yet
    //these are the objects we will pull from the API
    //makes sure view updates when data changes
//    var songs = listOf<Song>()
//        set (value) {
//            field = value
//            notifyDataSetChanged()
//        }

    //itemCount used to make sure we display all the data
    override fun getItemCount() = songs.size

    fun setSearchListItems(searchData: List<Song>)
    {
        Log.v("SearchListItems","recieved: " + searchData)
        songs = searchData as ArrayList<Song>
        notifyDataSetChanged()
        Log.v("SearchListItems","list: " + songs)

    }


    //creates view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongItemViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song,parent,false)
        return SongItemViewHolder(view, context, navController)
    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
//    : SongItemViewHolder = SongItemViewHolder.inflateFrom(parent)

    //binds data to the view
    override fun onBindViewHolder(holder: SongItemViewHolder, position: Int) {
        //val item = songs[position]
        holder.bind(position)
    }

    //defines the view
    class SongItemViewHolder (itemView: View, private val context: Context, var navController: NavController)
        : RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView!!.findViewById(R.id.songName)
        private val artistDuration: TextView = itemView!!.findViewById(R.id.artist)
        private val image: ImageView = itemView!!.findViewById(R.id.songImage)
        private var pos:Int = 0


        //listener for detail fragment, pass along song info
        init {
            itemView.setOnClickListener {
                Log.v("Navigating", "RecycleView Clicked")
                val action = searchFragmentDirections.actionSearchFragmentToDetailFragment(pos)
                navController.navigate(action)
            }
        }

        fun bind(position: Int){

            pos = position
            val currSong = songs.get(position)
            title.text = currSong.title

            //trying to find why artist is null
            val artist = currSong.author
            val duration =  currSong.duration

            //Log.d("Song Info", currSong.toString())
            artistDuration.text =  "By " + artist + duration

            Glide.with(context).load(currSong.thumbnail)
                .apply(RequestOptions().centerCrop())
                .into(image)
            }
        }
}

/*  SESE:
     WHEN YOU DO THE RECYCLEVIEW

     pass in the Song as a serializable as the following when creating the details fragment
     var bundle = Bundle() (or however it's made)

     bundle.putSerializable(song)
     -- where song is the Song object (in api.song.Song)

    */
