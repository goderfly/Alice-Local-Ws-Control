package alicews.models.genious.search


import com.google.gson.annotations.SerializedName


data class SearchResponseModel (

  @SerializedName("meta"     ) var meta     : Meta?     = Meta(),
  @SerializedName("response" ) var response : Response? = Response()

)