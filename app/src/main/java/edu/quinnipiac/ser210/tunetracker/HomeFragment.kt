package edu.quinnipiac.ser210.tunetracker

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import edu.quinnipiac.ser210.tunetracker.databinding.FragmentHomeBinding

class homeFragment : Fragment() {
    lateinit var navController: NavController
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.homeButton.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_homeFragment_to_searchFragment)
        }
        return view
    }
    private fun applyBackgroundColor() {
        val sharedPref = activity?.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val color = sharedPref?.getInt("BackgroundColor", Color.BLACK) ?: Color.BLACK
        view?.setBackgroundColor(color)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}