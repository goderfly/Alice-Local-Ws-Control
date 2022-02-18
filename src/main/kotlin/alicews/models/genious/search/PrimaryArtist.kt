package alicews.models.genious.search

import com.google.gson.annotations.SerializedName


data class PrimaryArtist (

  @SerializedName("api_path"         ) var apiPath        : String?  = null,
  @SerializedName("header_image_url" ) var headerImageUrl : String?  = null,
  @SerializedName("id"               ) var id             : Int?     = null,
  @SerializedName("image_url"        ) var imageUrl       : String?  = null,
  @SerializedName("is_meme_verified" ) var isMemeVerified : Boolean? = null,
  @SerializedName("is_verified"      ) var isVerified     : Boolean? = null,
  @SerializedName("name"             ) var name           : String?  = null,
  @SerializedName("url"              ) var url            : String?  = null

)