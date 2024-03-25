package edu.quinnipiac.ser210.tunetracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation

class detailFragment : Fragment() {

    lateinit var navController: NavController
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)


        view.findViewById<Button>(R.id.returnButton).setOnClickListener {
            navController.navigate(R.id.action_detailFragment_to_searchFragment)
        }
    }
}