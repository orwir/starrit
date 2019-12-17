package orwir.gazzit.common

import orwir.gazzit.common.extensions.KoinedShareable
import orwir.gazzit.common.extensions.Shareable
import orwir.gazzit.common.extensions.enumPref
import orwir.gazzit.model.AutoPlayVideo
import orwir.gazzit.model.LoadSourceImage

object Preferences : Shareable by KoinedShareable() {

    val loadSourceImage: LoadSourceImage by enumPref(
        key = "load_source_image",
        defaultValue = LoadSourceImage.ByClick
    )

    val autoPlayVideo: AutoPlayVideo by enumPref(
        key = "auto_play_video",
        defaultValue = AutoPlayVideo.Never
    )

}