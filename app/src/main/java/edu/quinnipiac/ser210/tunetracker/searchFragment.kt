package edu.quinnipiac.ser210.tunetracker

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
    private fun applyBackgroundColor() {
        val sharedPref = activity?.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val color = sharedPref?.getInt("BackgroundColor", Color.BLACK) ?: Color.BLACK
        view?.setBackgroundColor(color)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("searchFragment", "Fragment Created")
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.songsRecyclerView)
        recyclerAdapter = SongItemAdapter(requireContext(), Navigation.findNavController(view))
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerAdapter


        var search = view.findViewById<EditText>(R.id.searchEditText)

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