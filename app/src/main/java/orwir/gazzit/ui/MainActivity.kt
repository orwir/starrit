package orwir.gazzit.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import orwir.gazzit.authorize
import orwir.gazzit.util.provide
import java.util.*

class MainActivity : AppCompatActivity() {

    private val authViewModel by provide<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager
            .beginTransaction()
            .add(android.R.id.content, LoginFragment())
            .commit()

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.run(authViewModel::verify)
    }

}

class AuthViewModel : ViewModel() {

    private var requestState = ""
    private var code = ""

    fun authorize(openOAuth: (intent: Intent) -> Unit) {
        requestState = UUID.randomUUID().toString()
        authorize(requestState, openOAuth)
    }

    fun verify(uri: Uri) {
        if (uri.getQueryParameter("state") == requestState) {
            code = uri.getQueryParameter("code")!!
            Log.d("!!!!!!", uri.toString())
        }
    }

}