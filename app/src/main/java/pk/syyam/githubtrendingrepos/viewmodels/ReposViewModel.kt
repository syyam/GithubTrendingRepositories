package pk.syyam.githubtrendingrepos.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import pk.syyam.githubtrendingrepos.repository.GithubTrendingRepository
import javax.inject.Inject

@HiltViewModel
class ReposViewModel @Inject constructor(
    private val repository: GithubTrendingRepository,
) : ViewModel() {

    // initially the mutableLiveData will be empty
    private val repoData = MutableLiveData("")

    val repos = repoData.switchMap {
        repository.getSearchResults().cachedIn(viewModelScope)
    }

}