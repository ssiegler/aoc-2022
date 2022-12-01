data class Reader(val elves: List<List<Int>> = listOf(), val elf: List<Int> = listOf()) {
    fun read(line: String): Reader =
        when {
            line.isBlank() -> Reader(complete())
            else -> Reader(elves, elf + line.toInt())
        }

    fun complete(): List<List<Int>> =
        when {
            elf.isEmpty() -> elves
            else -> elves + listOf(elf)
        }
}

fun part1(input: List<String>): Int =
    input.fold(Reader(), Reader::read).complete().maxOf { it.sum() }

fun part2(input: List<String>): Int =
    input.fold(Reader(), Reader::read).complete().map { it.sum() }.sortedDescending().take(3).sum()

fun main() {

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
