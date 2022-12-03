package day03

import utils.readInput

@JvmInline
value class Rucksack(private val items: List<ItemType>) {
    private val firstCompartment: List<ItemType>
        get() = items.subList(0, items.size / 2)
    private val secondCompartment: List<ItemType>
        get() = items.subList(items.size / 2, items.size)

    val firstCommonType: ItemType
        get() = firstCompartment.first { it in secondCompartment }

    val itemTypes: Set<ItemType>
        get() = items.toSet()
}

fun fromLine(line: CharSequence) = Rucksack(line.map(::ItemType))

val priorities: Map<Char, Int> = (('a'..'z').zip(1..26) + ('A'..'Z').zip(27..52)).toMap()

@JvmInline
value class ItemType(private val value: Char) {
    val priority: Int
        get() = priorities[value] ?: error("No priority for $value")
}

fun part1(filename: String): Int =
    readInput(filename).map(::fromLine).map(Rucksack::firstCommonType).sumOf(ItemType::priority)

fun List<Rucksack>.commonType() = map { it.itemTypes }.reduce(Set<ItemType>::intersect).single()

fun part2(filename: String): Int =
    readInput(filename)
        .map(::fromLine)
        .chunked(3)
        .map(List<Rucksack>::commonType)
        .sumOf(ItemType::priority)

fun main() {
    val filename = "Day03"
    println(part1(filename))
    println(part2(filename))
}
