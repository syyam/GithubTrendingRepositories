package pk.syyam.githubtrendingrepos.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import pk.syyam.githubtrendingrepos.model.Items
import pk.syyam.githubtrendingrepos.network.pagingsource.FakeGithubTrendingPagingSource

@ExperimentalPagingApi
class FakeGithubTrendingRepository(
) : GithubTrendingRepository {


    override fun getSearchResults(): LiveData<PagingData<Items>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                maxSize = MAX_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { FakeGithubTrendingPagingSource() }
        ).liveData
    }
}