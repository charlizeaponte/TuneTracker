package edu.quinnipiac.ser210.tunetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import com.google.android.material.navigation.NavigationView
import android.content.Intent
import android.graphics.Color
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    // supposed to apply background color when the user selects settings, but it's not working right now
    private fun applyBackgroundColor() {
        val sharedPref = getSharedPreferences("AppSettings", MODE_PRIVATE)
        val color = sharedPref.getInt("BackgroundColor", Color.BLACK)
        val rootView = findViewById<ViewGroup>(R.id.main_container)
        rootView.setBackgroundColor(color)
    }
    // options menu from toolbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    // tree for when an item from the menu is selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                shareContent()
                true
            }
            R.id.action_settings -> {
                openSettings()
                true
            }
            R.id.action_help -> {
                showHelpInfo()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // brings up the share content options for user
    private fun shareContent() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Check out this great app!")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, null))
    }

    // options for selecting background color
    private fun openSettings() {
        val colors = arrayOf("Red", "Green", "Blue", "Gray")
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("Choose a background color")
        builder.setItems(colors) { _, which ->
            val chosenColor = when (which) {
                0 -> Color.RED
                1 -> Color.GREEN
                2 -> Color.BLUE
                3 -> Color.GRAY
                else -> Color.BLACK
            }
            changeBackgroundColor(chosenColor)
        }
        builder.show()
    }

    private fun changeBackgroundColor(color: Int) {
        // Save the color to SharedPreferences
        val sharedPref = getSharedPreferences("AppSettings", MODE_PRIVATE)
        with (sharedPref.edit()) {
            putInt("BackgroundColor", color)
            apply()
        }
    }

    private fun showHelpInfo() {
        Toast.makeText(this, "App version 1.0. This app uses a RestAPI to look up song lyrics, display song duration, artist name, and display artist photos. This app was created by Julia, Charlize and SeSe for Professor Ruby's SER210 Spring 2024 course.", Toast.LENGTH_LONG).show()
    }
}
