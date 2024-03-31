package edu.quinnipiac.ser210.tunetracker

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import edu.quinnipiac.ser210.tunetracker.api.lyrics.LyricsResult
import edu.quinnipiac.ser210.tunetracker.api.song.Song
import edu.quinnipiac.ser210.tunetracker.api.song.SongResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class detailFragment : Fragment() {

    var song_num = 0

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   recipient = arguments!!.getString("recipient")
        val bundle = arguments
        if (bundle == null) {
            Log.e("DetailFragment", "DetailFragment did not receive song_num")

            return
        }
        song_num = detailFragmentArgs.fromBundle(bundle).songNum

    }
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)


        view.findViewById<Button>(R.id.returnButton).setOnClickListener {
            navController.navigate(R.id.action_detailFragment_to_searchFragment)
        }



//for when the song is passed and passes it to the function
        var song = (savedInstanceState?.getSerializable("Song") as Song?)

        val image: ImageView = view.findViewById(R.id.imageView2)
        view.findViewById<TextView>(R.id.songNameTextView).text = songs.get(song_num).title
        val artist = songs.get(song_num).author
        val duration = songs.get(song_num).duration

        view.findViewById<TextView>(R.id.artistNameTextView).text = "By " + artist + " " + duration
        Glide.with(requireContext()).load(songs.get(song_num).thumbnail)
            .apply(RequestOptions().centerCrop())
            .into(image)


        if(song != null) {
            onSongReceived(song, view)
        }

    }

    fun onSongReceived(song: Song, view: View) {

        //sets text from song api to the view
        view.findViewById<TextView>(R.id.songNameTextView).text = song.title
        view.findViewById<TextView>(R.id.artistNameTextView).text = song.author


//Api for the lyrics
        val apiInterface = ApiInterface.create().getLyrics(song.videoId)
        if(apiInterface != null) {
            apiInterface
                .enqueue(object : Callback<LyricsResult?> {
                    override fun onResponse(
                        call: Call<LyricsResult?>,
                        response: Response<LyricsResult?>
                    ) {
                        Log.v("LyricsAPI", "Response Received")

                    }

                    override fun onFailure(call: Call<LyricsResult?>, t: Throwable) {
                        if (t != null) {
                            t.message?.let { Log.d("onFailure", it) }

                        }
                    }

                })
        }
    }
}