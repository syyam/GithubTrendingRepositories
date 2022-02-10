package pk.syyam.githubtrendingrepos.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import pk.syyam.githubtrendingrepos.model.Items

interface GithubTrendingRepository {

    fun getSearchResults() : LiveData<PagingData<Items>>

}