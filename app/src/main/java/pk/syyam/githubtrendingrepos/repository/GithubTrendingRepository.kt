package pk.syyam.githubtrendingrepos.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import pk.syyam.githubtrendingrepos.network.RetrofitService
import pk.syyam.githubtrendingrepos.network.pagingsource.GithubTrendingPagingSource
import javax.inject.Inject
import javax.inject.Singleton

const val NETWORK_PAGE_SIZE = 25
const val MAX_SIZE = 200

@Singleton
class GithubTrendingRepository @Inject constructor(private val retrofitService: RetrofitService) {

    fun getSearchResults() =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                maxSize = MAX_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { GithubTrendingPagingSource(retrofitService) }
        ).liveData
}