import day22.*
import geometry.Direction
import org.junit.jupiter.api.Test
import utils.readInput
import kotlin.test.assertEquals

private const val filename = "Day22Example"

class Day22Test {

    @Test
    fun `password calculation`() {
        assertEquals(6032, State(7 to 5, Direction.Right).password)
    }

    @Test
    fun `trace instructions`() {
        val input = readInput(filename)
        val board = input.takeWhile { it.isNotBlank() }.readBoard()
        val last = board.trace(input.last().readInstructions()).last()
        assertEquals(State(7 to 5, Direction.Right), last)
    }

    @Test
    fun part1() {
        assertEquals(6032, part1(filename))
    }
}
