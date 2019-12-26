package orwir.starrit.core.utl

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import orwir.starrit.core.util.squeeze

@RunWith(Parameterized::class)
class SqueezeTest(private val number: Long, private val expected: String) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0} -> {1}")
        fun data(): Collection<Array<Any>> = listOf(
            arrayOf(10, "10"),
            arrayOf(100, "100"),
            arrayOf(1_000, "1k"),
            arrayOf(1_010, "1k"),
            arrayOf(1_100, "1.1k"),
            arrayOf(1_000_000, "1M"),
            arrayOf(1_000_100, "1M"),
            arrayOf(1_100_000, "1.1M")
        )
    }

    @Test
    fun `given a number when squeeze() then return formatted number`() {
        assertThat(number.squeeze())
            .isEqualTo(expected)
    }

}
