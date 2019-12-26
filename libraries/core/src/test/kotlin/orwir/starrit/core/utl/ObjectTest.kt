package orwir.starrit.core.utl

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import orwir.starrit.core.util.createHashCode

class ObjectTest {

    @Test
    fun `given 1 when createHashCode() then return 31`() {
        assertThat(createHashCode(1))
            .isEqualTo(31)
    }

    @Test
    fun `given (1, 2, 3) when createHashCode() then return 186`() {
        assertThat(createHashCode(1, 2, 3))
            .isEqualTo(186)
    }

}