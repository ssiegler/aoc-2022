package day03

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

private const val filename = "Day03Example"

class Day03Test {

    @Test
    fun part1() {
        assertEquals(157, part1(filename))
    }

    @Test
    fun part2() {
        assertEquals(70, part2(filename))
    }
}
