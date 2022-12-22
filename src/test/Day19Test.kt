import day19.Blueprint
import day19.Material.*
import day19.readBlueprints
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

private const val filename = "Day19Example"

class Day19Test {

    @Test
    fun `reads blueprints`() {
        assertEquals(
            listOf(
                Blueprint(
                    1,
                    mapOf(
                        Ore to mapOf(Ore to 4),
                        Clay to mapOf(Ore to 2),
                        Obsidian to mapOf(Ore to 3, Clay to 14),
                        Geode to mapOf(Ore to 2, Obsidian to 7)
                    )
                ),
                Blueprint(
                    2,
                    mapOf(
                        Ore to mapOf(Ore to 2),
                        Clay to mapOf(Ore to 3),
                        Obsidian to mapOf(Ore to 3, Clay to 8),
                        Geode to mapOf(Ore to 3, Obsidian to 12)
                    )
                )
            ),
            readBlueprints(filename)
        )
    }

    @Test
    fun part1() {
        assertEquals(listOf(9, 12), readBlueprints(filename).map { it.maxGeodes(24) })
        assertEquals(33, day19.part1(filename))
    }

    @Test
    fun part2() {
        assertEquals(listOf(56, 62), readBlueprints(filename).map { it.maxGeodes(32) })
    }
}
