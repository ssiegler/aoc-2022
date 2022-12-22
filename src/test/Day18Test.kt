import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

private const val filename = "Day18Example"

class Day18Test {

    @Test
    fun part1() {
        assertEquals(64, day18.part1(filename))
    }

    @Test
    fun part2() {
        assertEquals(58, day18.part2(filename))
    }
}
