package edu.quinnipiac.ser210.tunetracker

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    companion object {
        const val THEME_CHANGED_ACTION = "edu.quinnipiac.ser210.tunetracker.THEME_CHANGED"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

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

    private fun shareContent() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Check out this great app!")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, null))
    }

    private fun openSettings() {
        val themes = arrayOf("Green", "Red", "Blue", "Grey", "Black")
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("Choose a Theme")
        builder.setItems(themes) { _, which ->
            val selectedTheme = themes[which]
            val sharedPrefs = getSharedPreferences("ThemePrefs", MODE_PRIVATE)
            sharedPrefs.edit().putString("SelectedTheme", selectedTheme).apply()

            applyTheme(selectedTheme)
            notifyThemeChange()
            recreate()
        }
        builder.show()
    }

    private fun applyTheme(themeName: String) {
    }

    private fun notifyThemeChange() {
        val intent = Intent(THEME_CHANGED_ACTION)
        sendBroadcast(intent)
    }

    private fun showHelpInfo() {
        Toast.makeText(this, "App version 1.0. This app uses a RestAPI to look up song lyrics, display song duration, artist name, and display artist photos. This app was created by Julia, Charlize and SeSe for Professor Ruby's SER210 Spring 2024 course.", Toast.LENGTH_LONG).show()
    }
}
