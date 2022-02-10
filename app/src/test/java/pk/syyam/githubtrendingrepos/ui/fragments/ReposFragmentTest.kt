package pk.syyam.githubtrendingrepos.ui.fragments

import android.app.LauncherActivity
import android.os.Build
import android.os.Looper.getMainLooper
import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import junit.framework.Assert.assertEquals
import kotlinx.android.synthetic.main.fragment_repos.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.bouncycastle.asn1.x500.style.RFC4519Style.owner
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import pk.syyam.githubtrendingrepos.MainCoroutineRule
import pk.syyam.githubtrendingrepos.R
import pk.syyam.githubtrendingrepos.adapters.TrendingReposAdapter
import pk.syyam.githubtrendingrepos.launchFragmentInHiltContainer
import pk.syyam.githubtrendingrepos.model.Items
import pk.syyam.githubtrendingrepos.model.Owner
import pk.syyam.githubtrendingrepos.ui.MainActivity
import java.util.*

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)

@Config(
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.O_MR1],
    application = HiltTestApplication::class
)
@ExperimentalCoroutinesApi

class ReposFragmentTest {
    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun init() {
        hiltAndroidRule.inject()
    }

    @Test
    fun testLaunchFragmentInHiltContainer() {
        launchFragmentInHiltContainer<ReposFragment> {
            shadowOf(getMainLooper()).idle();
            assert(
                this.view?.findViewById<TextView>(
                    R.id.emptyTv
                )?.text.toString() == "Check your internet connection and try again"
            )
        }

    }


}