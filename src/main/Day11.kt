package day11

import utils.readInput

typealias ThrowItem = Pair<Long, Int>

val ThrowItem.item
    get() = first

val ThrowItem.target
    get() = second

class Monkey(
    private var items: List<Long>,
    private val operation: Long.() -> Long,
    private val test: Long.() -> Boolean,
    private val whenTrue: Int,
    private val whenFalse: Int,
) {
    private var inspections: Int = 0

    val totalInspections: Int
        get() = inspections

    val currentItems: List<Long>
        get() = items

    fun catch(caught: List<Long>) {
        items = items + caught
    }

    fun onTurn(): List<ThrowItem> = items.map { it.inspect() }.also { items = emptyList() }

    private fun Long.inspect(): ThrowItem {
        inspections++
        val worryLevel = operation().div(3)
        return worryLevel to
            if (worryLevel.test()) {
                whenTrue
            } else {
                whenFalse
            }
    }
}

fun List<Monkey>.turn() {
    forEach {
        it.onTurn().groupBy(ThrowItem::target, ThrowItem::item).forEach { (target, items) ->
            get(target).catch(items)
        }
    }
}

fun List<Monkey>.businessLevel(): Int =
    map { it.totalInspections }.sortedDescending().take(2).reduce(Int::times)

fun List<String>.readMonkeys(): List<Monkey> = chunked(7).map(List<String>::readMonkey)

private fun List<String>.readMonkey() =
    Monkey(
        get(1).readItems(),
        get(2).readOperation(),
        get(3).readTest(),
        get(4).readTarget("true"),
        get(5).readTarget("false")
    )

private fun String.readItems(): List<Long> =
    removePrefix("  Starting items: ").split(", ".toRegex()).map { it.toLong() }

private fun String.readOperation(): (Long) -> Long {
    val (operator, operand) = removePrefix("  Operation: new = old ").split(' ')
    return when (operator) {
        "+" -> operation(operand, Long::plus)
        "*" -> operation(operand, Long::times)
        else -> error("Unknown operator: $operator")
    }
}

private fun operation(operand: String, operator: (Long, Long) -> Long): (Long) -> Long =
    when (operand) {
        "old" -> {
            { operator(it, it) }
        }
        else -> {
            operand.toLong().let { amount -> { operator(it, amount) } }
        }
    }

private fun String.readTest(): (Long) -> Boolean =
    removePrefix("  Test: divisible by ").toLong().let { divisor -> { it % divisor == 0L } }

private fun String.readTarget(case: String): Int =
    removePrefix("    If $case: throw to monkey ").toInt()

private const val filename = "Day11"

fun part1(filename: String): Int {
    val monkeys = readInput(filename).readMonkeys()
    repeat(20) { monkeys.turn() }
    return monkeys.businessLevel()
}

fun main() {
    println(part1(filename))
}
