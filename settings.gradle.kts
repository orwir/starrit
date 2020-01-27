rootProject.name = "starrit"

include(
    ":app",

    ":features:splash",
    ":features:login",
    ":features:feed",

    ":libraries:core",
    ":libraries:view",
    ":libraries:access",
    ":libraries:content",

    ":widgets:videoplayer",
    ":widgets:banner"
)