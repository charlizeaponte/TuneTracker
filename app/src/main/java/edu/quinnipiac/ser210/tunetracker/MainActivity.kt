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
        val sharedPrefs = getSharedPreferences("ThemePrefs", MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
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
        val themes = arrayOf("Green", "Red", "Blue", "Grey")
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("Choose a Theme")

        builder.setItems(themes) { _, which ->
            val selectedTheme = themes[which]

            val sharedPrefs = getSharedPreferences("ThemePrefs", MODE_PRIVATE)
            sharedPrefs.edit().putString("SelectedTheme", selectedTheme).apply()

            applyTheme(selectedTheme)

            recreate()
        }
        builder.show()
    }

    private fun applyTheme(themeName: String) {
        when (themeName) {
            "Green" -> setTheme(R.style.Theme_TuneTracker_Green)
            "Red" -> setTheme(R.style.Theme_TuneTracker_Red)
            "Blue" -> setTheme(R.style.Theme_TuneTracker_Blue)
            "Grey" -> setTheme(R.style.Theme_TuneTracker_Grey)
            else -> setTheme(R.style.Theme_TuneTracker)
        }
    }
    private fun showHelpInfo() {
        Toast.makeText(this, "App version 1.0. This app uses a RestAPI to look up song lyrics, display song duration, artist name, and display artist photos. This app was created by Julia, Charlize and SeSe for Professor Ruby's SER210 Spring 2024 course.", Toast.LENGTH_LONG).show()
    }
}
