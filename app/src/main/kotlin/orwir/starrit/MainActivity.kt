package orwir.starrit

import android.os.Bundle
import androidx.navigation.findNavController
import org.koin.core.parameter.parametersOf
import orwir.starrit.core.di.Scope
import orwir.starrit.view.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Scope.host.get<Navigator> { parametersOf(this, findNavController(R.id.navhost)) }
    }

}