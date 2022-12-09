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

data class Rope(val head: Position = 0 to 0, val tail: Position = 0 to 0) {
    fun move(direction: Direction): Rope {
        val head = head.move(direction)
        val tail = tail.follow(head)
        return Rope(head, tail)
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
            1 to 1 -> tail
            2 to 0 -> tail.x + 1 to tail.y
            -2 to 0 -> tail.x - 1 to tail.y
            0 to 2 -> tail.x to tail.y + 1
            0 to -2 -> tail.x to tail.y - 1
            2 to 1 -> tail.x + 1 to tail.y + 1
            2 to -1 -> tail.x + 1 to tail.y - 1
            -2 to 1 -> tail.x - 1 to tail.y + 1
            -2 to -1 -> tail.x - 1 to tail.y - 1
            1 to 2 -> tail.x + 1 to tail.y + 1
            1 to -2 -> tail.x + 1 to tail.y - 1
            -1 to 2 -> tail.x - 1 to tail.y + 1
            -1 to -2 -> tail.x - 1 to tail.y - 1
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

fun part1(filename: String): Int {
    val tailPositions = mutableSetOf<Position>()
    var rope = Rope()
    for (direction in readMoves(filename)) {
        rope = rope.move(direction)
        tailPositions.add(rope.tail)
    }
    return tailPositions.size
}

private fun readMoves(filename: String): List<Direction> =
    readInput(filename).map(String::readMoves).flatMap { (direction, count) ->
        generateSequence { direction }.take(count)
    }

private const val filename = "Day09"

fun main() {
    println(part1(filename))
}
