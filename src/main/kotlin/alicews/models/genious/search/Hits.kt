package alicews.models.genious.search

import com.google.gson.annotations.SerializedName


data class Hits (

  @SerializedName("highlights" ) var highlights : ArrayList<String> = arrayListOf(),
  @SerializedName("index"      ) var index      : String?           = null,
  @SerializedName("type"       ) var type       : String?           = null,
  @SerializedName("result"     ) var result     : Result?           = Result()

)