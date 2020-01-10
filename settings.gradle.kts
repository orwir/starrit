rootProject.name = "starrit"

include(
    ":app",

    ":features:splash",
    ":features:login",
    ":features:feed",

    ":libraries:core",
    ":libraries:view",
    ":libraries:authorization",
    ":libraries:listing",

    ":widgets:videoplayer",
    ":widgets:banner"
)