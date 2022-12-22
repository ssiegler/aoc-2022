import day20.grooveCoordinates
import day20.mix
import org.junit.jupiter.api.Test
import utils.readInput
import kotlin.test.assertEquals

private const val filename = "Day20Example"

class Day20Test {
    @Test
    fun `reads input`() {
        assertEquals(listOf(1, 2, -3, 3, -2, 0, 4), readInput(filename).map { it.toInt() })
    }

    @Test
    fun `groove coordinates`() {
        assertEquals(
            listOf(4, -3, 2),
            readInput(filename).map { it.toInt() }.mix().grooveCoordinates()
        )
    }

    @Test
    fun part1() {
        assertEquals(3, day20.part1(filename))
    }
}
