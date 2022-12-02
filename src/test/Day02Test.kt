import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

private const val FILENAME = "Day02Example"

class Day02Test {
    @Test
    fun part1() {
        assertEquals(15, part1(FILENAME))
    }

    @Test
    fun part2() {
        assertEquals(12, part2(FILENAME))
    }
}
