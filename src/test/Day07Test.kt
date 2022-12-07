package day07

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

private const val filename: String = "Day07Example"

class Day07Test {

    @Test
    fun part1() {
        assertEquals(95437, part1(filename))
    }

    @Test
    fun part2() {
        assertEquals(24933642, part2(filename))
    }
}
