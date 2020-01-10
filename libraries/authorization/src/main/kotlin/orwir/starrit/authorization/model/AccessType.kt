package orwir.starrit.authorization.model

import orwir.starrit.core.BuildConfig.REDDIT_URL_BASIC
import orwir.starrit.core.BuildConfig.REDDIT_URL_OAUTH

enum class AccessType {
    /**
     * User opens the app first time or cleared data
     */
    Unspecified,
    /**
     * User had authorization but token refresh was unsuccessful
     */
    Revoked,
    /**
     * User has authorization
     */
    Authorized,
    /**
     * User has access without authorization
     */
    Anonymous;

    /**
     * If user [Authorized] send requests to [REDDIT_URL_OAUTH] otherwise [REDDIT_URL_BASIC].
     */
    fun resolveBaseUrl() = if (this == Authorized) REDDIT_URL_OAUTH else REDDIT_URL_BASIC
}