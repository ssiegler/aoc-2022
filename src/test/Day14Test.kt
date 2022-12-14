import day14.readCaveWithFloor
import day14.toRocks
import org.junit.jupiter.api.Test
import utils.readInput
import kotlin.test.assertEquals

private const val filename = "Day14Example"

class Day14Test {

    @Test
    fun `read path`() {
        assertEquals(
            setOf(498 to 4, 498 to 5, 498 to 6, 497 to 6, 496 to 6),
            "498,4 -> 498,6 -> 496,6".toRocks()
        )
    }

    @Test
    fun part1() {
        assertEquals(24, day14.part1(filename))
    }

    @Test
    fun `adds floor`() {
        assertEquals(
            """
            ...........+...........
            .......................
            .......................
            .......................
            .........#...##........
            .........#...#.........
            .......###...#.........
            .............#.........
            .............#.........
            .....#########.........
            .......................
            #######################
            
        """
                .trimIndent(),
            readInput(filename).readCaveWithFloor().visualize()
        )
    }

    @Test
    fun part2() {
        assertEquals(93, day14.part2(filename))
    }
}
