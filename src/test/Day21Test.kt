import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

private const val filename = "Day21Example"

class Day21Test {

    @Test
    fun part1() {
        assertEquals(152, day21.part1(filename))
    }

    @Test
    fun part2() {
        assertEquals(301, day21.part2(filename))
    }
}
