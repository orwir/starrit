package orwir.starrit.core

import android.util.Log
import org.koin.core.logger.KOIN_TAG
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import timber.log.Timber

class KoinLoger : Logger() {
    override fun log(level: Level, msg: MESSAGE) {
        val priority = when (level) {
            Level.DEBUG -> Log.DEBUG
            Level.ERROR -> Log.ERROR
            Level.INFO -> Log.INFO
        }
        Timber.tag(KOIN_TAG).log(priority, msg)
    }
}