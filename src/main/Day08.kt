package day08

import day08.Direction.*
import utils.readInput
import kotlin.math.max

typealias Grid = List<String>

data class Tree(val height: Int) {
    operator fun compareTo(tree: Tree): Int = height.compareTo(tree.height)
}

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
            check(row = i, column = j, direction = Right)
            check(row = j, column = i, direction = Down)
            check(row = i, column = size - 1 - j, direction = Left)
            check(row = size - 1 - j, column = i, direction = Top)
        }
    }
    return visible.size
}

fun Grid.highestScenicScore(): Int {
    var maxScore = 0
    for (row in 1..size - 2) {
        for (column in 1..size - 2) {
            maxScore = max(score(row, column), maxScore)
        }
    }
    return maxScore
}

fun Grid.score(row: Int, column: Int): Int =
    when {
        row == 0 || column == 0 || row == size - 1 || column == size - 1 -> 0
        else ->
            Direction.values()
                .map(findTrees(column, row))
                .map { it.countVisible(tree(row, column)) }
                .reduce(Int::times)
    }

private fun Grid.findTrees(column: Int, row: Int): (Direction) -> List<Tree> = {
    when (it) {
        Right -> (column + 1 until size).map { tree(row, it) }
        Down -> (row + 1 until size).map { tree(it, column) }
        Left -> (column - 1 downTo 0).map { tree(row, it) }
        Top -> (row - 1 downTo 0).map { tree(it, column) }
    }
}

private fun List<Tree>.countVisible(origin: Tree): Int {
    var score = 0
    for (tree in this) {
        score += 1
        if (tree >= origin) {
            break
        }
    }
    return score
}

fun part1(filename: String): Int = readInput(filename).countVisibleTrees()

fun part2(filename: String): Int = readInput(filename).highestScenicScore()

private const val filename = "Day08"

fun main() {
    println(part1(filename))
    println(part2(filename))
}
