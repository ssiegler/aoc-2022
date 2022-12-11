import day11.part1
import day11.readMonkeys
import day11.turn
import org.junit.jupiter.api.Test
import utils.readInput
import kotlin.test.assertEquals

private const val filename = "Day11Example"

class Day11Test {
    private val monkeys = readInput(filename).readMonkeys()

    @Test
    fun `round 1`() {
        monkeys.turn()

        assertEquals(
            listOf(
                listOf<Long>(20, 23, 27, 26),
                listOf<Long>(2080, 25, 167, 207, 401, 1046),
                emptyList(),
                emptyList()
            ),
            monkeys.map { it.currentItems }
        )
    }

    @Test
    fun `round 20`() {
        repeat(20) { monkeys.turn() }

        assertEquals(
            listOf(
                listOf<Long>(10, 12, 14, 26, 34),
                listOf<Long>(245, 93, 53, 199, 115),
                emptyList(),
                emptyList()
            ),
            monkeys.map { it.currentItems }
        )
    }

    @Test
    fun `check activity`() {
        repeat(20) { monkeys.turn() }

        assertEquals(listOf(101, 95, 7, 105), monkeys.map { it.totalInspections })
    }

    @Test
    fun `monkey business`() {
        assertEquals(10605, part1(filename))
    }
}
