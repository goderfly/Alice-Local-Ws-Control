package alicews.models.genious.search

import com.google.gson.annotations.SerializedName


data class Stats (

  @SerializedName("unreviewed_annotations" ) var unreviewedAnnotations : Int?     = null,
  @SerializedName("concurrents"            ) var concurrents           : Int?     = null,
  @SerializedName("hot"                    ) var hot                   : Boolean? = null,
  @SerializedName("pageviews"              ) var pageviews             : Int?     = null

)