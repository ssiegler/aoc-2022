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
    val divisor: Long,
    private val whenTrue: Int,
    private val whenFalse: Int,
) {
    private var inspections: Long = 0

    val totalInspections: Long
        get() = inspections

    val currentItems: List<Long>
        get() = items

    fun catch(caught: List<Long>) {
        items = items + caught
    }

    fun onTurn(relief: Boolean): List<ThrowItem> =
        items.map { it.inspect(relief) }.also { items = emptyList() }

    private fun Long.inspect(relief: Boolean): ThrowItem {
        inspections++
        val worryLevel = operation().let { if (relief) it.div(3) else it }
        return worryLevel to
            if (worryLevel % divisor == 0L) {
                whenTrue
            } else {
                whenFalse
            }
    }
}

fun List<Monkey>.turn() {
    forEach {
        it.onTurn(relief = true).groupBy(ThrowItem::target, ThrowItem::item).forEach {
            (target, items) ->
            get(target).catch(items)
        }
    }
}

fun List<Monkey>.turnsWithoutRelief(n: Int) {
    val commonDivisor = map { it.divisor }.reduce(Long::times)
    repeat(n) {
        forEach {
            it.onTurn(relief = false).groupBy(ThrowItem::target, ThrowItem::item).forEach {
                (target, items) ->
                get(target).catch(items.map { item -> item % commonDivisor })
            }
        }
    }
}

fun List<Monkey>.businessLevel(): Long =
    map { it.totalInspections }.sortedDescending().take(2).reduce(Long::times)

fun List<String>.readMonkeys(): List<Monkey> = chunked(7).map(List<String>::readMonkey)

private fun List<String>.readMonkey() =
    Monkey(
        get(1).readItems(),
        get(2).readOperation(),
        get(3).readDivisor(),
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

private fun String.readDivisor(): Long = removePrefix("  Test: divisible by ").toLong()

private fun String.readTarget(case: String): Int =
    removePrefix("    If $case: throw to monkey ").toInt()

private const val filename = "Day11"

fun part1(filename: String): Long {
    val monkeys = readInput(filename).readMonkeys()
    repeat(20) { monkeys.turn() }
    return monkeys.businessLevel()
}

fun part2(filename: String): Long {
    val monkeys = readInput(filename).readMonkeys()
    monkeys.turnsWithoutRelief(10000)
    return monkeys.businessLevel()
}

fun main() {
    println(part1(filename))
    println(part2(filename))
}
