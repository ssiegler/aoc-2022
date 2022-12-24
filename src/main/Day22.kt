package day22

import geometry.Direction
import geometry.Position
import geometry.x
import geometry.y
import utils.readInput

enum class Tile {
    OPEN,
    WALL
}

typealias Board = Map<Position, Tile>

fun Iterable<String>.readBoard(): Board =
    withIndex()
        .flatMap { (i, row) ->
            row.withIndex().map { (j, column) ->
                when (column) {
                    '.' -> j to i to Tile.OPEN
                    '#' -> j to i to Tile.WALL
                    else -> null
                }
            }
        }
        .filterNotNull()
        .toMap()

sealed interface Instruction {
    object TurnLeft : Instruction
    object TurnRight : Instruction

    data class Move(val steps: Int) : Instruction
}

fun String.readInstructions(): Sequence<Instruction> =
    """\d+|R|L""".toRegex().findAll(this).map {
        when (it.value) {
            "R" -> Instruction.TurnRight
            "L" -> Instruction.TurnLeft
            else -> Instruction.Move(it.value.toInt())
        }
    }

data class State(val position: Position, val facing: Direction) {
    fun turnRight(): State =
        copy(
            facing =
                when (facing) {
                    Direction.Right -> Direction.Down
                    Direction.Down -> Direction.Left
                    Direction.Left -> Direction.Up
                    Direction.Up -> Direction.Right
                }
        )

    fun turnLeft(): State =
        copy(
            facing =
                when (facing) {
                    Direction.Right -> Direction.Up
                    Direction.Down -> Direction.Right
                    Direction.Left -> Direction.Down
                    Direction.Up -> Direction.Left
                }
        )

    fun Board.move(steps: Int): State {
        var position = position
        for (i in 1..steps) {
            val next = position.next()
            position =
                when (this[next]) {
                    Tile.WALL -> break
                    Tile.OPEN -> next
                    null -> wrap(position)
                }
        }
        return copy(position = position)
    }

    val password = (position.y + 1) * 1000 + (position.x + 1) * 4 + facing.ordinal

    private fun Board.wrap(position: Position): Position =
        with(position) {
            when (facing) {
                Direction.Right -> row(y).minOf { it.x } to y
                Direction.Down -> x to column(x).minOf { it.y }
                Direction.Left -> row(y).maxOf { it.x } to y
                Direction.Up -> x to column(x).maxOf { it.y }
            }.takeIf { this@wrap[it] == Tile.OPEN }
                ?: position
        }

    private fun Position.next(): Position =
        when (facing) {
            Direction.Right -> x + 1 to y
            Direction.Down -> x to y + 1
            Direction.Left -> x - 1 to y
            Direction.Up -> x to y - 1
        }
}

fun Board.trace(instructions: Sequence<Instruction>): Sequence<State> =
    instructions.scan(start()) { state, instruction ->
        with(state) {
            when (instruction) {
                Instruction.TurnLeft -> turnLeft()
                Instruction.TurnRight -> turnRight()
                is Instruction.Move -> move(instruction.steps)
            }
        }
    }

private fun Board.start() = State(row(0).minOf { it.x } to 0, Direction.Right)

private fun Board.row(y: Int) = keys.filter { it.y == y }

private fun Board.column(x: Int) = keys.filter { it.x == x }

private const val filename = "Day22"

fun part1(filename: String): Int {
    val input = readInput(filename)
    val board = input.takeWhile { it.isNotBlank() }.readBoard()
    return board.trace(input.last().readInstructions()).last().password
}

fun main() {
    println(part1(filename))
}
