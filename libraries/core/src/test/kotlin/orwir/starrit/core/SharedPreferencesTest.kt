package orwir.starrit.core

import android.content.SharedPreferences
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import org.junit.Test
import orwir.starrit.test.assertThrows

class SharedPreferencesTest {

    private val adapter: ObjectAdapter<TestObject> = mockk(relaxed = true)
    private val editor: SharedPreferences.Editor = mockk(relaxed = true)
    private val prefs: SharedPreferences = mockk(relaxed = true) {
        every { edit() } returns editor
        val value = slot<String>()
        every { getString(any(), capture(value)) } answers { value.captured }
    }

    @Test
    fun `given Nullable Object delegate when call accessors then SharedPreferences should be called`() {
        class Test {
            var test: TestObject? by objPref(prefs, adapter, "nullableObjectPref", null)
        }

        val tested = Test()
        val obj = TestObject()

        tested.test = obj
        tested.test

        verify { adapter.from(obj) }
        verify { prefs.edit() }
        verify { editor.putString("nullableObjectPref", "") }
        verify { editor.apply() }
        verify { prefs.getString("nullableObjectPref", "") }

        confirmVerified(adapter)
        confirmVerified(prefs)
        confirmVerified(editor)
    }

    @Test
    fun `given Enum delegate when call accessors then SharedPreferences should be called`() {
        class Test {
            var test: TestEnum by enumPref(prefs)
        }

        val tested = Test()

        tested.test = TestEnum.No
        tested.test

        verify { prefs.edit() }
        verify { editor.putString("Test.test", TestEnum.No.name) }
        verify { editor.apply() }
        verify { prefs.getString("Test.test", TestEnum.Yes.name) }

        confirmVerified(prefs)
        confirmVerified(editor)
    }

    @Test
    fun `given Primitive delegate when call accessors then SharedPreferences should be called`() {
        class Test {
            var test: String by pref(prefs)
        }

        val tested = Test()

        tested.test = "test value"
        tested.test

        verify { prefs.edit() }
        verify { editor.putString("Test.test", "test value") }
        verify { editor.apply() }
        verify { prefs.getString("Test.test", "") }

        confirmVerified(prefs)
        confirmVerified(editor)
    }

    @Test
    fun `given key and primitive class when call set() then put value into SharedPreferences`() {
        prefs["test"] = "test value"

        verify { prefs.edit() }
        verify { editor.putString("test", "test value") }
        verify { editor.apply() }

        confirmVerified(prefs)
        confirmVerified(editor)
    }

    @Test
    fun `given key and primitive class when call get() then SharedPreferences returns value by the key`() {
        prefs["test", "default value"]

        verify { prefs.getString("test", "default value") }

        confirmVerified(prefs)
        confirmVerified(editor)
    }

    @Test
    fun `given class and it's property when call getKey(Any, KProperty) then return simpleName+propertyName`() {
        class Test {
            val test: String = ""
        }

        val tested = Test()

        assertThat(getKey(tested, tested::test))
            .isEqualTo("Test.test")
    }

    @Test
    fun `given String class when call defaultType() then return empty string`() {
        assertThat(defaultForType<String>())
            .isEqualTo("")
    }

    @Test
    fun `given Int class when call defaultType() then return 0`() {
        assertThat(defaultForType<Int>())
            .isEqualTo(0)
    }

    @Test
    fun `given Boolean class when call defaultType() then return false`() {
        assertThat(defaultForType<Int>())
            .isEqualTo(0)
    }

    @Test
    fun `given Float class when call defaultType() then return 0F`() {
        assertThat(defaultForType<Int>())
            .isEqualTo(0)
    }

    @Test
    fun `given Long class when call defaultType() then return 0L`() {
        assertThat(defaultForType<Int>())
            .isEqualTo(0)
    }

    @Test
    fun `given non-primitive class when call defaultType() then throw IllegalArgumentException`() {
        assertThrows<IllegalArgumentException> { defaultForType<Any>() }
            .hasMessageThat()
            .startsWith("Default value not found for type")
    }

}

class TestObject

enum class TestEnum {
    Yes,
    No
}