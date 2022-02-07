package pk.syyam.githubtrendingrepos.model

import com.google.gson.annotations.SerializedName

data class TrendingReposResponse(


    @SerializedName("items")
    val items: List<Items> = emptyList(),

)