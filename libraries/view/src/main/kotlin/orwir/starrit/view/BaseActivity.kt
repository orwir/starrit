package orwir.starrit.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import orwir.starrit.core.di.createScope
import orwir.starrit.core.di.setCurrentHost
import orwir.starrit.link.LinkDispatcher

abstract class BaseActivity : AppCompatActivity() {

    val linkDispatcher: LinkDispatcher = LinkDispatcher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createScope()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.let(linkDispatcher::onLinkReceived)
    }

    override fun onStart() {
        super.onStart()
        setCurrentHost()
    }

}