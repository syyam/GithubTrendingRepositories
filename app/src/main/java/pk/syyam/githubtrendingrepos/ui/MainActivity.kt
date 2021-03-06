package pk.syyam.githubtrendingrepos.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import dagger.hilt.android.AndroidEntryPoint
import pk.syyam.githubtrendingrepos.R
import pk.syyam.githubtrendingrepos.databinding.ActivityMainBinding
import pk.syyam.githubtrendingrepos.utils.PreferenceKeys
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @set:Inject
    internal var sharedPreferences: SharedPreferences? = null
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()


    }

    private fun setupView() {
        setSupportActionBar(findViewById(R.id.toolbar_top))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false);

        if (sharedPreferences?.getString(PreferenceKeys.KEY_PREF_THEME, "")
                .equals(getString(R.string.string_dark_theme))
        ) {
            setTheme(R.style.AppThemeDark)

        } else {
            setTheme(R.style.AppThemeLight)

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_theme, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_light -> {
                sharedPreferences?.edit()
                    ?.putString(
                        PreferenceKeys.KEY_PREF_THEME,
                        getString(R.string.string_light_theme)
                    )?.apply()

                recreate();

                true
            }

            R.id.menu_dark -> {
                sharedPreferences?.edit()
                    ?.putString(
                        PreferenceKeys.KEY_PREF_THEME,
                        getString(R.string.string_dark_theme)
                    )?.apply()
                recreate();
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}