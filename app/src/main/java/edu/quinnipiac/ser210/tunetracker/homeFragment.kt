package edu.quinnipiac.ser210.tunetracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText

class homeFragment : Fragment() {
    lateinit var navController: NavController
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            navController = Navigation.findNavController(view)


            view.findViewById<Button>(R.id.homeButton).setOnClickListener {
                navController.navigate(R.id.action_homeFragment_to_searchFragment)
            }
        }
    }
