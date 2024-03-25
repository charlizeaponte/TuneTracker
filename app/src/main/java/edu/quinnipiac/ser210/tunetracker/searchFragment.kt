package edu.quinnipiac.ser210.tunetracker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class searchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: SongItemAdapter

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_main, container, false)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.songsRecyclerView)
        recyclerAdapter = SongItemAdapter(requireContext(), Navigation.findNavController(view))
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerAdapter

        val apiInterface = ApiInterface.create().getSearch()
        if (apiInterface != null) {
            apiInterface.enqueue(object : Callback<ArrayList<searchData?>?>  {
                override fun onResponse(
                    call: Call<ArrayList<searchData?>?>,
                    response: Response<ArrayList<searchData?>?>
                ) {
                    if (response?.body() != null) {
                        //recyclerAdapter.setHerosListItems(response.body() !! as ArrayList<Hero>)

                    }
                }

                override fun onFailure(call: Call<ArrayList<searchData?>?>, t: Throwable) {
                    if (t != null) {
                        t.message?.let { Log.d("onFailure", it) }

                    }
                }
            })
        }
    }
}