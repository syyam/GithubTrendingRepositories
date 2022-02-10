package pk.syyam.githubtrendingrepos.ui.fragments

import javax.inject.Inject
import javax.inject.Singleton
import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.android.synthetic.main.fragment_repos.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.testing.UninstallModules

import org.hamcrest.CoreMatchers.containsString
import pk.syyam.githubtrendingrepos.R


@ExperimentalCoroutinesApi
@UninstallModules(ReposFragmentTest.ProductionModule::class)
@HiltAndroidTest


class ReposFragmentTest {
    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)


    @Inject
    lateinit var someString: String

    @Before
    fun init() {
        hiltAndroidRule.inject()
    }

    @Test
    fun hiltTest() {
        assertThat(someString, containsString("TEST string"))
    }

    @Test
    fun mainFragmentTest() {
        val scenario = launchFragmentInContainer<ReposFragment>()
    }


    @Test
    fun testLaunchFragmentInHiltContainer() {
        launchFragmentInHiltContainer<ReposFragment> {
            assert(
                this.view?.findViewById<TextView>(
                    R.id.emptyTv
                )?.text.toString() == "Check your internet connection and try again"
            )
        }

    }

    @Module
    @InstallIn(SingletonComponent::class)
    object ProductionModule {


        @Singleton
        @Provides
        fun provideString(): String {
            return "This is a TEST string I'm providing for injection"
        }
    }
}