package edu.quinnipiac.ser210.tunetracker

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {
   //Might need ? after search in the get method
    @GET("search")
    fun getSearch(): Call<ArrayList<searchData?>?>?
    @GET("music/lyrics/plain")
    fun getLyrics(): Call<ArrayList<lyrics?>?>?

    companion object{

        var BASE_URL = "https://youtube-music-api3.p.rapidapi.com/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}