package orwir.starrit.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import orwir.starrit.link.UriDispatcher

abstract class BaseActivity : AppCompatActivity() {

    val uriDispatcher: UriDispatcher = UriDispatcher()

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.let(uriDispatcher::onUri)
    }

}