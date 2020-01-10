package orwir.android.material.banner

typealias OnBannerButtonClick = (BannerInterface.() -> Unit)

interface BannerInterface {

    fun dismiss()

}