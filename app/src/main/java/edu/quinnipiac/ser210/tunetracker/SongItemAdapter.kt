package edu.quinnipiac.ser210.tunetracker
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController

class SongItemAdapter(val context: Context, var navController: NavController) : RecyclerView.Adapter<SongItemAdapter.SongItemViewHolder>() {


    //current error- Song object is not defined yet
    //these are the objects we will pull from the API
    //makes sure view updates when data changes
    var data = listOf<Result>()
    set (value) {
            field = value
            notifyDataSetChanged()
        }

    //itemCount used to make sure we display all the data
    override fun getItemCount() = data.size

//    fun setSearchListItems(searchData: ArrayList<searchData>)
//    {
//        data = searchData
//        notifyDataSetChanged()
//    }

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
                    val view = layoutInflater.inflate(R.layout.song_item, parent, false) as TextView
                    return SongItemViewHolder(view)

                }
            }

            fun bind(item: Result) {
                rootView.text = item.title
            }
        }
}