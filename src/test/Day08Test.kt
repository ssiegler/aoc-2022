package day08

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

private const val filename = "Day08Example"

class Day08Test {
    @Test
    fun part1() {
        assertEquals(21, part1(filename))
    }

    @Test
    fun part2() {
        assertEquals(8, part2(filename))
    }
}
