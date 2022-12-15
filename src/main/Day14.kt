package day14

import geometry.*
import utils.readInput

private val source: Position = 500 to 0

class Cave(private val rocks: Set<Position>) {
    private val filled = rocks.toMutableSet()
    private val depth = rocks.maxOf(Position::y)

    fun fill(): Int =
        generateSequence { drop() }.takeWhile { it == Drop.SETTLES && source !in filled }.count() +
            if (source in filled) 1 else 0

    fun visualize(): String = buildString {
        for (y in 0..depth) {
            for (x in -depth..depth) {
                append(
                    when (source.x + x to y) {
                        source -> '+'
                        in rocks -> '#'
                        in filled -> 'o'
                        else -> '.'
                    }
                )
            }
            append("\n")
        }
    }

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

fun List<String>.readCave(): Cave = readRocks().let(::Cave)

fun List<String>.readRocks() = map(String::toRocks).reduce(Set<Position>::union)

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

fun part2(filename: String): Int {
    val lines = readInput(filename)
    val cave = lines.readCaveWithFloor()
    return cave.fill()
}

fun List<String>.readCaveWithFloor(): Cave {
    val rocks = readRocks()
    val depth = rocks.maxOf { it.y } + 2
    return Cave(rocks + (-depth..depth).map { it + source.x to depth }.toSet())
}

fun main() {
    println(part1(filename))
    println(part2(filename))
}
