package day16

import utils.readInput
import java.util.*
import kotlin.math.max

data class Valves(
    private val flows: Map<String, Int>,
    private val tunnels: Map<String, List<String>>
) {
    private val flowValves =
        flows.entries.filter { (_, flow) -> flow > 0 }.map { (valve, _) -> valve }

    private val distances: Map<Pair<String, String>, Int> by lazy {
        val distances = mutableMapOf<Pair<String, String>, Int>()
        for (u in tunnels.keys) {
            distances[u to u] = 0
            for (v in tunnels[u] ?: emptyList()) {
                distances[u to v] = 1
            }
        }
        for (k in tunnels.keys) {
            for (i in tunnels.keys) {
                for (j in tunnels.keys) {
                    val whole = distances[i to j]
                    val first = distances[i to k]
                    val second = distances[k to j]
                    if (
                        first != null && second != null && (whole == null || whole > first + second)
                    ) {
                        distances[i to j] = first + second
                    }
                }
            }
        }

        return@lazy distances.filterKeys { (_, dest) -> dest in flowValves }
    }

    data class State(
        val remaining: Int,
        val position: String,
        val openValves: List<String>,
        val rate: Int,
        val total: Int
    ) {
        fun flow(): Int = total + remaining * rate
    }

    fun maximalPressure(): Int {
        var maxFlow = 0
        val queue = ArrayDeque<State>()
        queue.push(State(30, "AA", emptyList(), 0, 0))
        while (queue.isNotEmpty()) {
            val state = queue.pop()
            maxFlow = max(maxFlow, state.flow())
            if (state.remaining >= 0) {
                val rate = flows[state.position]!!
                if (state.position !in state.openValves && rate > 0) {
                    queue.push(
                        State(
                            state.remaining - 1,
                            state.position,
                            state.openValves + state.position,
                            state.rate + rate,
                            state.total + state.rate
                        )
                    )
                } else {
                    for (destination in flowValves) {
                        if (destination !in state.openValves) {
                            val distance = distances[state.position to destination]!!
                            val remaining = state.remaining - distance
                            if (remaining > 1) {
                                queue.push(
                                    State(
                                        remaining,
                                        destination,
                                        state.openValves,
                                        state.rate,
                                        state.total + (state.rate * distance)
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
        return maxFlow
    }
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

fun part1(filename: String) = readInput(filename).readValves().maximalPressure()

private const val filename = "Day16"

fun main() {
    println(part1(filename))
}
