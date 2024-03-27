package edu.quinnipiac.ser210.tunetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("MainActivity","Created")
        setContentView(R.layout.activity_main)


    }
}
