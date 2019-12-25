package orwir.starrit.core.extension

import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import retrofit2.Retrofit

class RetrofitTest {

    @Test
    fun `given Interface and Retrofit instance when call service() then Retrofit_create called`() {
        val tested: Retrofit = mockk(relaxed = true)

        service(tested, Retrofit::class.java)

        verify { tested.create(Retrofit::class.java) }

        confirmVerified(tested)
    }

}