package day09

import Direction
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

private fun Rope.print(): String {
    var result = ""
    for (y in 5 downTo 0) {
        for (x in 0..5) {
            result +=
                when {
                    head == x to y -> "H"
                    tail == x to y -> "T"
                    else -> "."
                }
        }
        result += "\n"
    }
    return result
}

private const val filename = "Day09Example"

class Day09Test {
    @Test
    fun part1() {
        assertEquals(13, part1(filename))
    }

    @Test
    fun name() {
        var rope = Rope()
        println(rope.print())
        repeat(4) {
            rope = rope.move(Direction.Right)
            println()
            println(rope.print())
        }
        repeat(4) {
            rope = rope.move(Direction.Up)
            println()
            println(rope.print())
        }
    }
}
