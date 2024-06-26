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
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.quinnipiac.ser210.tunetracker.api.song.SongResult
import edu.quinnipiac.ser210.tunetracker.databinding.FragmentSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class searchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: SongItemAdapter

    private val binding get() = _binding!!

    // Define the BroadcastReceiver
    private val themeChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateFragmentBackground()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("searchFragment", "onCreate")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.v("searchFragment", "onCreateView")
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onStart() {
        super.onStart()
        context?.registerReceiver(themeChangeReceiver, IntentFilter(MainActivity.THEME_CHANGED_ACTION))
        updateFragmentBackground()
    }
    override fun onStop() {
        super.onStop()
        // Unregister the receiver
        context?.unregisterReceiver(themeChangeReceiver)
    }
    private fun updateFragmentBackground() {
        // Ensure SharedPreferences are accessed correctly
        val sharedPrefs = activity?.getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE)
        val selectedTheme = sharedPrefs?.getString("SelectedTheme", "")
        val color = when (selectedTheme) {
            "Green" -> R.color.green
            "Red" -> R.color.red
            "Blue" -> R.color.blue
            "Grey" -> R.color.grey
            "Black" -> R.color.black
            else -> R.color.black
        }
        color?.let { binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), it)) }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("searchFragment", "Fragment Created")
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.songsRecyclerView
        recyclerAdapter = SongItemAdapter(requireContext(), Navigation.findNavController(view))
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerAdapter


        var search = binding.searchEditText

        binding.searchButton.setOnClickListener {
            Log.v("button", "Searched for " + search.text.toString())
            val apiInterface = ApiInterface.create().getSearch(search.text.toString(), "song")
            if (apiInterface != null) {
                apiInterface
                    .enqueue(object : Callback<SongResult?> {
                        override fun onResponse(
                            call: Call<SongResult?>,
                            response: Response<SongResult?>
                        ) {
                            Log.v("API Response", "I just responded")

                            if (response?.body() != null) {
                                var songs = (response.body()!! as SongResult).result;
                                Log.v("API Response", "songs: " + songs)
                                recyclerAdapter.setSearchListItems(songs)
                            }
                            updateFragmentBackground()
                        }

                        override fun onFailure(call: Call<SongResult?>, t: Throwable) {
                            if (t != null) {
                                t.message?.let { Log.d("onFailure", it) }

                            }
                        }
                    })
            }
        }
    }
}