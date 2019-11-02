package orwir.gazzit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import org.koin.android.ext.android.inject
import orwir.gazzit.authorization.AuthorizationRepository
import orwir.gazzit.common.AuthorizationHolder

class MainActivity : AppCompatActivity(), AuthorizationHolder {

    private val authorizationRepository: AuthorizationRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.let(authorizationRepository::completeAuthorization)
    }

    override fun request() {
        findNavController(android.R.id.content).navigate(R.id.action_global_authorizationFragment)
    }

}