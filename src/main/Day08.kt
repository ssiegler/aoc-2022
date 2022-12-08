package day08

import utils.readInput

typealias Grid = List<String>

data class Tree(val height: Int)

fun Grid.tree(row: Int, column: Int) = get(row)[column].toString().toInt().let(::Tree)

enum class Direction {
    Right,
    Down,
    Left,
    Top,
}

fun Grid.countVisibleTrees(): Int {
    val visible = mutableMapOf<Pair<Int, Int>, Tree>()

    for (i in indices) {
        val max = mutableMapOf<Direction, Int>()
        fun check(row: Int, column: Int, direction: Direction) {
            val tree = tree(row, column)
            if (tree.height > max.getOrDefault(direction, Int.MIN_VALUE)) {
                visible[row to column] = tree
                max[direction] = tree.height
            }
        }

        for (j in indices) {
            check(row = i, column = j, direction = Direction.Right)
            check(row = j, column = i, direction = Direction.Down)
            check(row = i, column = size - 1 - j, direction = Direction.Left)
            check(row = size - 1 - j, column = i, direction = Direction.Top)
        }
    }
    return visible.size
}

fun part1(filename: String): Int = readInput(filename).countVisibleTrees()

fun part2(filename: String): Int = TODO()

private const val filename = "Day08"

fun main() {
    println(part1(filename))
    println(part2(filename))
}
