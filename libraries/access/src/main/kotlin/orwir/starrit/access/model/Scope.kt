package orwir.starrit.access.model

import java.util.*

internal enum class Scope {
    Identity,
    Edit,
    Flair,
    History,
    Modconfig,
    Modflair,
    Modlog,
    Modposts,
    Modwiki,
    Mysubreddits,
    Privatemessages,
    Read,
    Report,
    Save,
    Submit,
    Subscribe,
    Vote,
    Wikiedit,
    Wikiread;

    fun asParameter() = name.toLowerCase(Locale.ENGLISH)
}