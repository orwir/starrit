package orwir.gazzit.model.listing

enum class Kind(val raw: String) {
    Comment("t1"),
    Account("t2"),
    Submission("t3"),
    Message("t4"),
    Subreddit("t5"),
    Trophy("t6"),
    MoreChildren("more"),
    Listing("Listing"),
    Multireddit("LabeledMulti"),
    LiveUpdate("LiveUpdate"),
    LiveThread("LiveThread"),
    TrophyList("TrophyList"),
    WikiPage("wikipage"),
    ModAction("modaction")
}