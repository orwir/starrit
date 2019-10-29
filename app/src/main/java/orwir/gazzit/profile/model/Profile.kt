package orwir.gazzit.profile.model

import com.squareup.moshi.Json

data class Profile(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "over_18") val over18: Boolean,
    @Json(name = "pref_autoplay") val autoplay: Boolean,
    @Json(name = "pref_video_autoplay") val videoAutoplay: Boolean
)