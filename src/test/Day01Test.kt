package day01

import org.junit.jupiter.api.Test
import utils.readInput
import kotlin.test.assertEquals

class Day01Test {
    @Test
    fun `reads calories by elf`() {
        val calories: List<List<Int>> =
            readInput("Day01Example").fold(Reader(), Reader::read).complete()

        assertEquals(
            listOf(
                listOf(1000, 2000, 3000),
                listOf(4000),
                listOf(5000, 6000),
                listOf(7000, 8000, 9000),
                listOf(10000),
            ),
            calories
        )
    }

    @Test
    fun `find max calories sum`() {
        assertEquals(24000, part1(readInput("Day01Example")))
    }

    @Test
    fun `find sum of top 3`() {
        assertEquals(45000, part2(readInput("Day01Example")))
    }
}
