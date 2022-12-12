import day11.part1
import day11.readMonkeys
import day11.turn
import day11.turnsWithoutRelief
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

        assertEquals(listOf<Long>(101, 95, 7, 105), monkeys.map { it.totalInspections })
    }

    @Test
    fun `monkey business`() {
        assertEquals(10605L, part1(filename))
    }

    @Test
    fun `20 rounds without relief`() {
        monkeys.turnsWithoutRelief(20)

        assertEquals(listOf<Long>(99, 97, 8, 103), monkeys.map { it.totalInspections })
    }

    @Test
    fun `1000 rounds without relief`() {
        monkeys.turnsWithoutRelief(1000)

        assertEquals(listOf<Long>(5204, 4792, 199, 5192), monkeys.map { it.totalInspections })
    }

    @Test
    fun `10000 rounds without relief`() {
        monkeys.turnsWithoutRelief(10000)

        assertEquals(listOf<Long>(52166, 47830, 1938, 52013), monkeys.map { it.totalInspections })
    }

    @Test
    fun part2() {
        assertEquals(2713310158, day11.part2(filename))
    }
}
