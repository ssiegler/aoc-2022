import day20.grooveCoordinates
import day20.mix
import day20.readEncrypted
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

private const val filename = "Day20Example"

class Day20Test {
    @Test
    fun `reads input`() {
        assertEquals(listOf<Long>(1, 2, -3, 3, -2, 0, 4), readEncrypted(filename))
    }

    @Test
    fun `groove coordinates`() {
        assertEquals(listOf<Long>(4, -3, 2), readEncrypted(filename).mix().grooveCoordinates())
    }

    @Test
    fun part1() {
        assertEquals(3, day20.part1(filename))
    }

    @Test
    fun part2() {
        assertEquals(1623178306, day20.part2(filename))
    }
}
