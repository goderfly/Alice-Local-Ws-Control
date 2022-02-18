package alicews.models.genious.search

import alicews.models.genious.search.PrimaryArtist
import alicews.models.genious.search.Stats
import com.google.gson.annotations.SerializedName


data class Result (

    @SerializedName("annotation_count"             ) var annotationCount          : Int?           = null,
    @SerializedName("api_path"                     ) var apiPath                  : String?        = null,
    @SerializedName("artist_names"                 ) var artistNames              : String?        = null,
    @SerializedName("full_title"                   ) var fullTitle                : String?        = null,
    @SerializedName("header_image_thumbnail_url"   ) var headerImageThumbnailUrl  : String?        = null,
    @SerializedName("header_image_url"             ) var headerImageUrl           : String?        = null,
    @SerializedName("id"                           ) var id                       : Int?           = null,
    @SerializedName("lyrics_owner_id"              ) var lyricsOwnerId            : Int?           = null,
    @SerializedName("lyrics_state"                 ) var lyricsState              : String?        = null,
    @SerializedName("path"                         ) var path                     : String?        = null,
    @SerializedName("pyongs_count"                 ) var pyongsCount              : Int?           = null,
    @SerializedName("song_art_image_thumbnail_url" ) var songArtImageThumbnailUrl : String?        = null,
    @SerializedName("song_art_image_url"           ) var songArtImageUrl          : String?        = null,
    @SerializedName("stats"                        ) var stats                    : Stats?         = Stats(),
    @SerializedName("title"                        ) var title                    : String?        = null,
    @SerializedName("title_with_featured"          ) var titleWithFeatured        : String?        = null,
    @SerializedName("url"                          ) var url                      : String?        = null,
    @SerializedName("primary_artist"               ) var primaryArtist            : PrimaryArtist? = PrimaryArtist()

)