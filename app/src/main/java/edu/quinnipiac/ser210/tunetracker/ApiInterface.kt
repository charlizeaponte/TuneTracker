package edu.quinnipiac.ser210.tunetracker

import edu.quinnipiac.ser210.tunetracker.api.lyrics.LyricsResult
import edu.quinnipiac.ser210.tunetracker.api.lyrics.lyrics
import edu.quinnipiac.ser210.tunetracker.api.song.SongResult
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiInterface {
   //Might need ? after search in the get method
    @GET("search")
    @Headers("X-RapidAPI-Key:832c38ddcfmshee53ce9983b62bfp187fd9jsn18973a142442", "X-RapidAPI-Host:youtube-music-api3.p.rapidapi.com")
    fun getSearch(@Query("q") search:String, @Query("type") type: String): Call<SongResult??>?

    @GET("music/lyrics/plain")
    @Headers("X-RapidAPI-Key:832c38ddcfmshee53ce9983b62bfp187fd9jsn18973a142442", "X-RapidAPI-Host:youtube-music-api3.p.rapidapi.com")

    fun getLyrics(@Query("id") id:String): Call<LyricsResult?>?

    companion object{

        var BASE_URL = "https://youtube-music-api3.p.rapidapi.com/"

        fun create() : ApiInterface {

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging) // <-- this is the important line!
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}