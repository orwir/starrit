package orwir.starrit

import android.os.Bundle
import org.koin.androidx.scope.currentScope
import orwir.starrit.view.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        currentScope.declare(this)
        supportFragmentManager.fragmentFactory = BaseFragmentFactory(currentScope)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}