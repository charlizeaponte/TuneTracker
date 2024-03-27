package edu.quinnipiac.ser210.tunetracker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.findNavController
import edu.quinnipiac.ser210.tunetracker.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var navController: NavController
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.homeButton.setOnClickListener {
            Log.d("homeButton Listener", "Code has been reached")
            view.findNavController()
                .navigate(R.id.action_homeFragment_to_searchFragment)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    }
