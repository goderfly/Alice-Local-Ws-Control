package alicews.models.genious.search

import alicews.models.genious.search.Hits
import com.google.gson.annotations.SerializedName


data class Response (

  @SerializedName("hits" ) var hits : ArrayList<Hits> = arrayListOf()

)