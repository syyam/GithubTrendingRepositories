package pk.syyam.githubtrendingrepos.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import pk.syyam.githubtrendingrepos.R
import pk.syyam.githubtrendingrepos.preference.PreferenceKeys
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @set:Inject
    internal var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        if (sharedPreferences?.getString(PreferenceKeys.KEY_PREF_THEME, "")
                .equals(getString(R.string.string_dark_theme))
        ) {
            setTheme(R.style.AppThemeDark)

            Toast.makeText(this, "dark", Toast.LENGTH_SHORT).show()

        } else {
            setTheme(R.style.AppThemeLight)
            Toast.makeText(this, "light", Toast.LENGTH_SHORT).show()

        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_theme, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_light -> {
                sharedPreferences?.edit()
                    ?.putString(PreferenceKeys.KEY_PREF_THEME, getString(R.string.string_light_theme))?.apply()

                recreate();

                true
            }

            R.id.menu_dark -> {
                sharedPreferences?.edit()
                    ?.putString(PreferenceKeys.KEY_PREF_THEME, getString(R.string.string_dark_theme))?.apply()
                recreate();
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}