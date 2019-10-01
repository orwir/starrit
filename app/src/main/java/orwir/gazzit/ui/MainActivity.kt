package orwir.gazzit.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import orwir.gazzit.util.provide

class MainActivity : AppCompatActivity() {

    private val authViewModel by provide<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager
            .beginTransaction()
            .add(android.R.id.content, LoginFragment())
            .commit()

        authViewModel.log()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

}

class AuthViewModel : ViewModel() {

    fun log() {
        Log.d("!!!!", "Instance ${this}")
    }

}