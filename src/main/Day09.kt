package day09

import Direction
import utils.readInput

typealias Position = Pair<Int, Int>

val Position.x
    get() = first

val Position.y
    get() = second

fun Position.move(direction: Direction): Position =
    when (direction) {
        Direction.Right -> x + 1 to y
        Direction.Down -> x to y - 1
        Direction.Left -> x - 1 to y
        Direction.Up -> x to y + 1
    }

data class Rope(val knots: List<Position> = listOf(0 to 0, 0 to 0)) {
    fun move(direction: Direction): Rope {
        val head = knots[0].move(direction)
        val tail = knots[1].follow(head)
        return Rope(listOf(head, tail))
    }

    private fun Position.follow(head: Position): Position =
        when (head.x - x to head.y - y) {
            -1 to -1,
            -1 to 0,
            -1 to 1,
            0 to -1,
            0 to 0,
            0 to 1,
            1 to -1,
            1 to 0,
            1 to 1 -> this
            2 to 0 -> x + 1 to y
            -2 to 0 -> x - 1 to y
            0 to 2 -> x to y + 1
            0 to -2 -> x to y - 1
            2 to 1 -> x + 1 to y + 1
            2 to -1 -> x + 1 to y - 1
            -2 to 1 -> x - 1 to y + 1
            -2 to -1 -> x - 1 to y - 1
            1 to 2 -> x + 1 to y + 1
            1 to -2 -> x + 1 to y - 1
            -1 to 2 -> x - 1 to y + 1
            -1 to -2 -> x - 1 to y - 1
            else -> error("Broken rope $this")
        }
}

fun String.readMoves() =
    when (get(0)) {
        'R' -> Direction.Right
        'D' -> Direction.Down
        'L' -> Direction.Left
        'U' -> Direction.Up
        else -> error("Unknown direction ${get(0)}")
    } to substring(2).toInt()

fun part1(filename: String): Int =
    readMoves(filename).simulate().map { it.knots.last() }.toSet().size

internal fun readMoves(filename: String): List<Direction> =
    readInput(filename).map(String::readMoves).flatMap { (direction, count) ->
        generateSequence { direction }.take(count)
    }

internal fun List<Direction>.simulate(): List<Rope> =
    scan(Rope()) { rope, direction -> rope.move(direction) }

private const val filename = "Day09"

fun main() {
    println(part1(filename))
}
