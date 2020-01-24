package orwir.starrit.content.internal.post

import android.view.View
import android.view.ViewGroup
import orwir.starrit.content.databinding.ViewContentImageBinding
import orwir.starrit.content.post.ImagePost

@Suppress("FunctionName")
internal fun PostContentBinder.ImageContent(post: ImagePost, parent: ViewGroup): View =
    ViewContentImageBinding
        .inflate(inflater, parent, true)
        .apply {
            image.loadImageData(post, hud.progress)
            hud.fullscreen.setOnClickListener { navigation.openFullscreen(post) }
        }
        .root