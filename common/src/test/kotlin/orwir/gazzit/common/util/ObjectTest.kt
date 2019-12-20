package orwir.gazzit.common.util

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ObjectTest {

    @Test
    fun `given 1 when createHashCode() then return 31`() {
        Assertions.assertEquals(31, createHashCode(1))
    }

    @Test
    fun `given (1, 2, 3) when createHashCode() then return 186`() {
        Assertions.assertEquals(186, createHashCode(1, 2, 3))
    }

}