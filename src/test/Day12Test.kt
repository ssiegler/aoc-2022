package day12

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

private const val filename = "Day12Example"

class Day12Test {
    @Test
    fun part1() {
        assertEquals(31, part1(filename))
    }

    @Test
    fun part2() {
        assertEquals(29, part2(filename))
    }
}
