package orwir.gazzit

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import org.koin.dsl.module

val localModule = module {

    single<SharedPreferences> {
        get<Application>().getSharedPreferences("GazzitPreferences", Context.MODE_PRIVATE)
    }

}