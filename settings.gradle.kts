rootProject.name = "starrit"

include(
    ":app",

    ":features:splash",
    ":features:login",
    ":features:feed",

    ":libraries:core",
    ":libraries:view",
    ":libraries:access",
    ":libraries:listing",

    ":widgets:videoplayer",
    ":widgets:banner"
)