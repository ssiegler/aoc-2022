package day14

import geometry.*
import utils.readInput

private val source: Position = 500 to 0

class Cave(rocks: Set<Position>) {
    private val filled = rocks.toMutableSet()
    private val depth = rocks.maxOf(Position::y)

    fun fill(): Int = generateSequence { drop() }.takeWhile { it == Drop.SETTLES }.count()

    private enum class Drop {
        SETTLES,
        KEEPS_FALLING
    }

    private fun drop(): Drop {
        var position = source
        while (position.y <= depth) {
            val down = position.move(Direction.Up)
            val next =
                listOf(down, down.move(Direction.Left), down.move(Direction.Right)).firstOrNull {
                    it !in filled
                }
            if (next == null) {
                filled.add(position)
                return Drop.SETTLES
            }
            position = next
        }
        return Drop.KEEPS_FALLING
    }
}

fun List<String>.readCave(): Cave = map(String::toRocks).reduce(Set<Position>::union).let(::Cave)

fun String.toRocks(): Set<Position> =
    split(" -> ".toRegex())
        .map(String::toPosition)
        .windowed(2)
        .flatMap { (a, b) -> a.straightLineTo(b) }
        .toSet()

fun String.toPosition(): Position = split(",").map(String::toInt).let { (x, y) -> x to y }

fun Position.straightLineTo(other: Position): List<Position> =
    when {
        x == other.x -> (y towards other.y).map { x to it }
        y == other.y -> (x towards other.x).map { it to y }
        else -> error("No straight line from $this to $other")
    }

private infix fun Int.towards(end: Int) =
    when {
        this <= end -> this..end
        else -> (end..this).reversed()
    }

private const val filename = "Day14"

fun part1(filename: String): Int {
    val cave = readInput(filename).readCave()
    return cave.fill()
}

fun main() {
    println(part1(filename))
}
