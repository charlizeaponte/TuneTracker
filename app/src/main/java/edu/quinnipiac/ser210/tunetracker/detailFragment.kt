package edu.quinnipiac.ser210.tunetracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import edu.quinnipiac.ser210.tunetracker.api.lyrics.LyricsResult
import edu.quinnipiac.ser210.tunetracker.api.song.Song
import edu.quinnipiac.ser210.tunetracker.api.song.SongResult
import edu.quinnipiac.ser210.tunetracker.databinding.FragmentDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class detailFragment : Fragment() {

    var song_num = 0
    var lyrics = "loading lyrics..."

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    lateinit var navController: NavController

    private val themeChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateFragmentBackground()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   recipient = arguments!!.getString("recipient")
        val bundle = arguments
        if (bundle == null) {
            Log.e("DetailFragment", "DetailFragment did not receive song_num")

            return
        }
        song_num = detailFragmentArgs.fromBundle(bundle).songNum
        view?.findViewById<TextView>(R.id.lyricText)?.setText(lyrics)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)


        binding.returnButton.setOnClickListener {
            navController.navigate(R.id.action_detailFragment_to_searchFragment)
        }



//for when the song is passed and passes it to the function
        var song = (savedInstanceState?.getSerializable("Song") as Song?)

        val image = binding.imageView2
        binding.songNameTextView.text = songs[song_num].title
        val artist = songs.get(song_num).author
        val duration = songs.get(song_num).duration

        binding.artistNameTextView.text = "By $artist $duration"

        Glide.with(requireContext()).load(songs.get(song_num).thumbnail)
            .apply(RequestOptions().centerCrop())
            .into(image)

        onSongReceived(song , view)
        binding.lyricText.text = lyrics

        updateFragmentBackground()

    }
    override fun onStart() {
        super.onStart()
        context?.registerReceiver(themeChangeReceiver, IntentFilter(MainActivity.THEME_CHANGED_ACTION))
        updateFragmentBackground()
    }

    override fun onStop() {
        super.onStop()
        context?.unregisterReceiver(themeChangeReceiver)
    }

    private fun updateFragmentBackground() {
        val context = context ?: return
        val sharedPrefs = activity?.getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE)
        val selectedTheme = sharedPrefs?.getString("SelectedTheme", null)
        val colorResId = when (selectedTheme) {
            "Green" -> R.color.green
            "Red" -> R.color.red
            "Blue" -> R.color.blue
            "Grey" -> R.color.grey
            "Black" -> R.color.black
            else -> R.color.black
        }
        val color = ContextCompat.getColor(context, colorResId)
        binding.root.setBackgroundColor(color)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Nullify the binding when the view is destroyed to avoid memory leaks
    }

    fun onSongReceived(song: Song?, view: View) {

        //sets text from song api to the view
//        view.findViewById<TextView>(R.id.songNameTextView).text = song.title
//        view.findViewById<TextView>(R.id.artistNameTextView).text = song.author

        val video_Id = songs.get(song_num).videoId
        Log.v("API Response", video_Id.toString())

//Api for the lyrics
        val apiInterface = ApiInterface.create().getLyrics(video_Id)
        if(apiInterface != null) {
            apiInterface
                .enqueue(object : Callback<LyricsResult?> {
                    override fun onResponse(
                        call: Call<LyricsResult?>,
                        response: Response<LyricsResult?>
                    ) {
                        Log.v("LyricsAPI", "Response Received")
                        if (response?.body() != null) {
                            lyrics = (response.body()!! as LyricsResult).description.text
                            if (lyrics != null) {
                                Log.v("API Response", "setting lyrics: $lyrics")
                                binding.lyricText.text = lyrics
                            } else {
                                // Handle the case where lyrics is null
                                Log.e("API Response", "Lyrics is null")
                                binding.lyricText.text = "no lyrics found"

                            }
                        }

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