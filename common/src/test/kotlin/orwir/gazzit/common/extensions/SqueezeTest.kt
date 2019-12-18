package orwir.gazzit.common.extensions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SqueezeTest {

    @Test
    fun `given 10 when squeeze() then return 10`() {
        assertEquals("10", 10.squeeze())
    }

    @Test
    fun `given 100 when squeeze() then return 100`() {
        assertEquals("100", 100.squeeze())
    }

    @Test
    fun `given 1_000 when squeeze() then return 1k`() {
        assertEquals("1k", 1_000.squeeze())
    }

    @Test
    fun `given 1_010 when squeeze() then return 1k`() {
        assertEquals("1k", 1_010.squeeze())
    }

    @Test
    fun `given 1_100 when squeeze() then return 1,1k`() {
        assertEquals("1.1k", 1_100.squeeze())
    }

    @Test
    fun `given 1_000_000 when squeeze() then return 1m`() {
        assertEquals("1M", 1_000_000.squeeze())
    }

    @Test
    fun `given 1_000_100 when squeeze() then return 1m`() {
        assertEquals("1M", 1_000_100.squeeze())
    }

    @Test
    fun `given 1_100_000 when squeeze() then return 1,1m`() {
        assertEquals("1.1M", 1_100_000.squeeze())
    }

}