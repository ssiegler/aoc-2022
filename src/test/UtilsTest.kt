package utils

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UtilsTest {
    @Test
    fun name() {
        val lines = readInput("test")

        assertEquals(listOf("first line", "second line"), lines)
    }
}
