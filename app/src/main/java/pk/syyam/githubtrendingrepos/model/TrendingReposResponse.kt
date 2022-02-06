package pk.syyam.githubtrendingrepos.model

import com.google.gson.annotations.SerializedName

data class TrendingReposResponse(

    @SerializedName("total_count")
    val totalCount: Int = 0,

    @SerializedName("items")
    val items: List<Repo> = emptyList(),

    val nextPage: Int? = null
)