package pk.syyam.githubtrendingrepos.network.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import pk.syyam.githubtrendingrepos.model.Repo
import pk.syyam.githubtrendingrepos.network.RetrofitService
import retrofit2.HttpException
import java.io.IOException

private const val REPOS_STARTING_PAGE_INDEX = 1

// PagingSource is the base class for loading snapshots of data into a stream of PagingData
class GithubTrendingPagingSource(
    private val githubApi: RetrofitService,
) : PagingSource<Int, Repo>() {


    // load(params: LoadParams<Int>) function will be called by the Paging library to asynchronously
    // fetch more data to be displayed as the user scrolls around.
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val pageIndex = params.key ?: REPOS_STARTING_PAGE_INDEX

        return try {
            Log.d("aaaaaaaa", "aaaaaaaaaa")

            val response = githubApi.getTrendingRepos(
                page = pageIndex
            )
            Log.d("aaaaaaaa", "bbbbbbbbbb")

            val repos = response.items

            Log.d("aaaaaaaa", repos.toString() + "asdasd")
//            This is where I am going to retrieve my items from a service (in my case, Retrofit)
//            and return them wrapped into a LoadResult type.
            return LoadResult.Page(
                data = repos,
                prevKey = if (pageIndex == REPOS_STARTING_PAGE_INDEX) null else pageIndex - 1,
                nextKey = if (repos.isEmpty()) null else pageIndex + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}