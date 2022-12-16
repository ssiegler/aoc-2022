package day16

import utils.readInput

data class Valves(
    private val flows: Map<String, Int>,
    private val tunnels: Map<String, List<String>>
) {

    private val cache = mutableMapOf<Triple<String, List<String>, Int>, Pair<Int, List<String>>>()

    fun maxPressure(
        start: String = "AA",
        minutes: Int = 30,
        openValves: List<String> = emptyList(),
        accumulatedFlow: Int = 0,
    ): Pair<Int, List<String>> {
        if (minutes <= 0) return accumulatedFlow to openValves

        val key = Triple(start, openValves, minutes)
        return cache[key]
            ?: findMaxPressure(start, openValves, minutes, accumulatedFlow).also { cache[key] = it }
    }

    private fun findMaxPressure(
        start: String,
        openValves: List<String>,
        minutes: Int,
        accumulatedFlow: Int
    ): Pair<Int, List<String>> =
        (if (flows[start]!! > 0 && !openValves.contains(start)) {
                tunnels[start]!!.map {
                    maxPressure(
                        it,
                        minutes - 2,
                        openValves + start,
                        accumulatedFlow + flows[start]!! * (minutes - 1)
                    )
                }
            } else {
                emptyList()
            } + tunnels[start]!!.map { maxPressure(it, minutes - 1, openValves, accumulatedFlow) })
            .maxBy { (flow, _) -> flow }
}

fun List<String>.readValves(): Valves {
    val flows = mutableMapOf<String, Int>()
    val tunnels = mutableMapOf<String, List<String>>()
    forEach { line ->
        val valves = "[A-Z][A-Z]".toRegex().findAll(line).map { it.value }.toList()
        flows[valves[0]] = """\d+""".toRegex().find(line)!!.value.toInt()
        tunnels[valves[0]] = valves.subList(1, valves.size)
    }
    return Valves(flows, tunnels)
}

fun part1(filename: String) = readInput(filename).readValves().maxPressure().first

private const val filename = "Day16"

fun main() {
    println(part1(filename))
}
