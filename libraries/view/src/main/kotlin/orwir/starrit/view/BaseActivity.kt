package orwir.starrit.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import orwir.starrit.core.link.LinkDispatcher

abstract class BaseActivity : AppCompatActivity() {

    internal val linkDispatcher: LinkDispatcher = LinkDispatcher()

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.let(linkDispatcher::onLinkReceived)
    }

}