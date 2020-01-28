package orwir.starrit.main

import android.os.Bundle
import org.koin.androidx.scope.currentScope
import orwir.starrit.R
import orwir.starrit.main.internal.MainFragmentFactory
import orwir.starrit.view.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        currentScope.declare(this)
        supportFragmentManager.fragmentFactory = MainFragmentFactory(currentScope)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}