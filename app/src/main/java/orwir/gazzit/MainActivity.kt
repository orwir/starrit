package orwir.gazzit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import orwir.gazzit.authorization.AuthorizationRepository

class MainActivity : AppCompatActivity() {

    private val authorizationRepository: AuthorizationRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // getKoin().declare(Navigator(this, findNavController(R.id.navhost)))
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.let(authorizationRepository::authorizationFlowComplete)
    }

//    override fun request() {
//        findNavController(android.R.id.content).navigate(NavGraphDirections.globalToAuthorization())
//    }

}