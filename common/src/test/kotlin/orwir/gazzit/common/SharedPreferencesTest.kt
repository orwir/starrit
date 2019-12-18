package orwir.gazzit.common

import android.content.SharedPreferences
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class SharedPreferencesTest {

    private val editor: SharedPreferences.Editor = mockk(relaxed = true)
    private val sharedPrefs: SharedPreferences = mockk(relaxed = true) {
        val defSlot = slot<String>()
        every { edit() } returns editor
        every { getString(any(), capture(defSlot)) } answers {
            defSlot.captured
        }
    }

    @Nested
    inner class SimpleStringPrefTest {

        private var tested: String by pref(sharedPrefs, "stringPref", "default value")

        @Test
        fun `given String pref when set value then get Editor and invoke putString(), apply()`() {
            // when
            tested = "value"

            // then
            verify { sharedPrefs.edit() }
            verify { editor.putString("stringPref", "value") }
            verify { editor.apply() }

            confirmVerified(sharedPrefs)
            confirmVerified(editor)
        }

        @Test
        fun `given String with default value as 'default value' pref when get value then invoke getString()`() {
            // when
            val actual = tested

            // then
            verify { sharedPrefs.getString("stringPref", "default value") }
            Assertions.assertEquals("default value", actual)

            confirmVerified(sharedPrefs)
            confirmVerified(editor)
        }

    }

    private enum class TestEnum {
        Yes,
        No
    }

    @Nested
    inner class EnumPrefTest {

        private var tested: TestEnum by enumPref(sharedPrefs, "enumPref")

        @Test
        fun `given Enum pref when set value then get Editor and invoke putString(), apply()`() {
            // when
            tested = TestEnum.No

            // then
            verify { sharedPrefs.edit() }
            verify { editor.putString("enumPref", TestEnum.No.name) }
            verify { editor.apply() }

            confirmVerified(sharedPrefs)
            confirmVerified(editor)
        }

        @Test
        fun `given Enum with default value as first element in enum pref when get value then invoke getString()`() {
            // when
            val actual = tested

            // then
            verify { sharedPrefs.getString("enumPref", TestEnum.Yes.name) }
            Assertions.assertEquals(TestEnum.Yes, actual)

            confirmVerified(sharedPrefs)
            confirmVerified(editor)
        }
    }

    class TestObject

    @Nested
    inner class NullableObjectPrefTest {

        private val adapter: ObjectAdapter<TestObject> = mockk(relaxed = true)
        private var tested: TestObject? by objPref(
            sharedPrefs,
            adapter,
            "nullableObjectPref",
            null
        )

        @Test
        fun `given Object pref when set value then get Editor and invoke putString(), apply()`() {
            // given
            val obj = TestObject()

            // when
            tested = obj

            // then
            verify { adapter.from(obj) }
            verify { sharedPrefs.edit() }
            verify { editor.putString("nullableObjectPref", "") }
            verify { editor.apply() }

            confirmVerified(adapter)
            confirmVerified(sharedPrefs)
            confirmVerified(editor)
        }

        @Test
        fun `given Object pref with null as default value when get value then invoke getString()`() {
            // when
            val actual = tested

            // then
            verify { sharedPrefs.getString("nullableObjectPref", "") }
            Assertions.assertEquals(null, actual)

            confirmVerified(adapter)
            confirmVerified(sharedPrefs)
            confirmVerified(editor)
        }

    }

}