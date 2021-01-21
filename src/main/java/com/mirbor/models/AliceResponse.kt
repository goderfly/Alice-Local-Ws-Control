package com.mirbor.models

import com.google.gson.annotations.SerializedName

data class AliceResponse(
        @SerializedName("extra") var extra: Any,
        @SerializedName("id") var id: String,
        @SerializedName("sentTime") var sentTime: Long,
        @SerializedName("state") var state: State
)


data class Hdmi(
        @SerializedName("capable") var capable: Boolean,
        @SerializedName("present") var present: Boolean
)



data class PlayerState(
        @SerializedName("duration") var duration: Double,
        @SerializedName("extra") var extra: Any,
        @SerializedName("hasNext") var hasNext: Boolean,
        @SerializedName("hasPause") var hasPause: Boolean,
        @SerializedName("hasPlay") var hasPlay: Boolean,
        @SerializedName("hasPrev") var hasPrev: Boolean,
        @SerializedName("hasProgressBar") var hasProgressBar: Boolean,
        @SerializedName("id") var id: String,
        @SerializedName("liveStreamText") var liveStreamText: String,
        @SerializedName("progress") var progress: Int,
        @SerializedName("showPlayer") var showPlayer: Boolean,
        @SerializedName("subtitle") var subtitle: String,
        @SerializedName("title") var title: String,
        @SerializedName("type") var type: String
)

data class State(
        @SerializedName("aliceState") var aliceState: String,
        @SerializedName("canStop") var canStop: Boolean,
        @SerializedName("hdmi") var hdmi: Hdmi,
        @SerializedName("playerState") var playerState: PlayerState,
        @SerializedName("playing") var playing: Boolean,
        @SerializedName("timeSinceLastVoiceActivity") var timeSinceLastVoiceActivity: Int,
        @SerializedName("volume") var volume: Int
)