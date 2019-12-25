package orwir.starrit.core

import android.util.Log
import io.mockk.spyk
import io.mockk.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.koin.core.logger.Level
import timber.log.Timber

@RunWith(Parameterized::class)
class KoinLoggerTest(private val koin: Level, private val timber: Int) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0} -> {1}")
        fun data(): Collection<Array<Any>> = listOf(
            arrayOf(Level.DEBUG, Log.DEBUG),
            arrayOf(Level.ERROR, Log.ERROR),
            arrayOf(Level.INFO, Log.INFO)
        )
    }

    private val tree: Timber.Tree = spyk()
    private val tested: KoinLogger = KoinLogger()

    init {
        Timber.plant(tree)
    }

    @Test
    fun `given log level from koin when log() then invoke corresponding log level in the Timber`() {
        tested.log(koin, "random message")

        verify { tree.log(timber, "random message") }

        //confirmVerified(tree) cannot do this because getTag() is package private
    }

}