package day05

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

private const val filename = "Day05Example"

class Day05Test {
    @Test
    fun part1() {
        assertEquals("CMZ", part1(filename))
    }

    @Test
    fun part2() {
        assertEquals("MCD", part2(filename))
    }
}
