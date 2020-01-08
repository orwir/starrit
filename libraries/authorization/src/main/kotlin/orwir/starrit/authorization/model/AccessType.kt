package orwir.starrit.authorization.model

import orwir.starrit.core.BuildConfig.REDDIT_URL_BASIC
import orwir.starrit.core.BuildConfig.REDDIT_URL_OAUTH

enum class AccessType {
    Unspecified,
    Authorized,
    Anonymous;

    fun resolveBaseUrl() = if (this == Authorized) REDDIT_URL_OAUTH else REDDIT_URL_BASIC
}