package edu.quinnipiac.ser210.tunetracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import edu.quinnipiac.ser210.tunetracker.databinding.FragmentHomeBinding

class homeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Define the BroadcastReceiver
    private val themeChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateFragmentBackground()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
        updateFragmentBackground()
    }

    override fun onStart() {
        super.onStart()
        context?.registerReceiver(themeChangeReceiver, IntentFilter(MainActivity.THEME_CHANGED_ACTION))
    }

    override fun onStop() {
        super.onStop()
        // Unregister the receiver
        context?.unregisterReceiver(themeChangeReceiver)
    }

    private fun updateFragmentBackground() {
        val sharedPrefs = activity?.getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE)
        val selectedTheme = sharedPrefs?.getString("SelectedTheme", "")
        val color = when (selectedTheme) {
            "Green" -> R.color.green
            "Red" -> R.color.red
            "Blue" -> R.color.blue
            "Grey" -> R.color.grey
            "Black" -> R.color.black
            else -> R.color.black
        }
        color?.let { binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), it)) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
