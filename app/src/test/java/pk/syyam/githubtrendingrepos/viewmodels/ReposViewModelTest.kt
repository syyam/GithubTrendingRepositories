package pk.syyam.githubtrendingrepos.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import pk.syyam.githubtrendingrepos.MainCoroutineRule
import pk.syyam.githubtrendingrepos.repository.FakeGithubTrendingRepository
import junit.framework.Assert.assertEquals
import org.mockito.Mock
import org.mockito.Mockito
import pk.syyam.githubtrendingrepos.model.Items
import pk.syyam.githubtrendingrepos.model.Owner
import pk.syyam.githubtrendingrepos.network.pagingsource.FakeGithubTrendingPagingSource
import pk.syyam.githubtrendingrepos.network.pagingsource.GithubTrendingPagingSource
import pk.syyam.githubtrendingrepos.repository.GithubTrendingRepository
import pk.syyam.githubtrendingrepos.repository.MAX_SIZE
import pk.syyam.githubtrendingrepos.repository.NETWORK_PAGE_SIZE


@ExperimentalCoroutinesApi
class ReposViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val githubTrendingRepository = Mockito.mock(GithubTrendingRepository::class.java)

    private lateinit var viewModel: ReposViewModel

    @OptIn(ExperimentalPagingApi::class)
    @Before
    fun setup() {
        viewModel = ReposViewModel(FakeGithubTrendingRepository())
    }

    @Test
    fun liveDataWorking() {

//        val value = viewModel.repos.observeForever()
//        assertThat(value).isEqualTo("asd")
        Mockito.`when`(githubTrendingRepository.getSearchResults()).thenReturn(
            Pager(
                config = PagingConfig(
                    pageSize = NETWORK_PAGE_SIZE,
                    maxSize = MAX_SIZE,
                    enablePlaceholders = true
                ),
                pagingSourceFactory = { FakeGithubTrendingPagingSource() }
            ).liveData
        )

        viewModel.repos
    }

}