package geometry

import kotlin.math.absoluteValue

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

fun Position.manhattenDistance(other: Position): Int =
    (x - other.x).absoluteValue + (y - other.y).absoluteValue
