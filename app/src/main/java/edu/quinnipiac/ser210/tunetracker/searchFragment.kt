package edu.quinnipiac.ser210.tunetracker

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





//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_main, container, false)
//    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("searchFragment", "Fragment Created");
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.songsRecyclerView)
        recyclerAdapter = SongItemAdapter(requireContext(), Navigation.findNavController(view))
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerAdapter


        var search = view.findViewById<EditText>(R.id.searchEditText)

        binding.searchButton.setOnClickListener {
            Log.v("button", "Searched for " + search.text.toString());
            val apiInterface = ApiInterface.create().getSearch(search.text.toString(), "song")
            if (apiInterface != null) {
                apiInterface
                    .enqueue(object : Callback<APIResult?> {
                        override fun onResponse(
                            call: Call<APIResult?>,
                            response: Response<APIResult?>
                        ) {
                            Log.v("API Response", "I just responded");
                            if (response?.body() != null) {
                                var songs = (response.body()!! as APIResult).result;
                                recyclerAdapter.setSearchListItems(songs)
                            }
                        }

                        override fun onFailure(call: Call<APIResult?>, t: Throwable) {
                            if (t != null) {
                                t.message?.let { Log.d("onFailure", it) }

                            }
                        }

                    })
            }
        }
//                apiInterface.enqueue(object : Callback<ArrayList<APIResult?>?>  {
//                    override fun onResponse(
//                        call: Call<ArrayList<APIResult?>?>,
//                        response: Response<ArrayList<APIResult?>?>
//                    ) {

//                    }
//
//                    override fun onFailure(call: Call<ArrayList<APIResult?>?>, t: Throwable) {
//
//                    }
//                })





    }
}