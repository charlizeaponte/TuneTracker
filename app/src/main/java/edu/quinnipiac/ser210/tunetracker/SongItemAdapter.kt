package edu.quinnipiac.ser210.tunetracker
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import edu.quinnipiac.ser210.tunetracker.api.song.Song

class SongItemAdapter(val context: Context, var navController: NavController) : RecyclerView.Adapter<SongItemAdapter.SongItemViewHolder>() {


    //current error- Song object is not defined yet
    //these are the objects we will pull from the API
    //makes sure view updates when data changes
    var data = listOf<Song>()
    set (value) {
            field = value
            notifyDataSetChanged()
        }

    //itemCount used to make sure we display all the data
    override fun getItemCount() = data.size

    fun setSearchListItems(searchData: List<Song>)
    {
        data = searchData
        notifyDataSetChanged()
    }

//    fun setHerosListItems(heroes: ArrayList<Hero>){
//        heroList = heroes
//        notifyDataSetChanged()
//    }

    //creates view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : SongItemViewHolder = SongItemViewHolder.inflateFrom(parent)

    //binds data to the view
    override fun onBindViewHolder(holder: SongItemViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    //defines the view
    class SongItemViewHolder (val rootView: TextView)
        : RecyclerView.ViewHolder(rootView) {

            companion object{
                fun inflateFrom(parent: ViewGroup): SongItemViewHolder {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val view = layoutInflater.inflate(R.layout.item_song, parent, false) as TextView
                    return SongItemViewHolder(view)

                }
            }

            fun bind(item: Song) {
                rootView.text = item.title
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
