package day19

import utils.readInput
import java.lang.Integer.max

enum class Material {
    Ore,
    Clay,
    Obsidian,
    Geode
}

typealias Cost = Map<Material, Int>

data class Blueprint(val id: Int, val costs: Map<Material, Cost>) {

    private val maxNeeded: Cost =
        costs.values
            .flatMap(Cost::asSequence)
            .groupingBy { it.key }
            .aggregate { _, accumulator, element, _ -> max(element.value, accumulator ?: 0) }

    fun qualityLevel(minutes: Int) = id * maxGeodes(minutes)

    fun maxGeodes(minutes: Int): Int {
        var maxGeodes = 0
        val stack = ArrayDeque<State>()
        stack.addLast(State(emptyMap(), mapOf(Material.Ore to 1), minutes))
        while (stack.isNotEmpty()) {
            val state = stack.removeLast()
            if (state.potential(this) < maxGeodes) continue
            maxGeodes = max(maxGeodes, state.estimate)
            for ((robot, costs) in costs) {
                if (state.robots(robot) >= maxNeeded(robot)) continue
                val demand = state.demand(costs)
                if (demand.any { (material, _) -> state.robots(material) == 0 }) continue
                val delta =
                    demand.maxOfOrNull { (material, demand) ->
                        val robots = state.robots(material)
                        (demand + robots - 1) / robots
                    }
                        ?: 0
                if (delta < state.time) {
                    stack.addLast(state.addRobot(robot, costs, delta))
                }
            }
        }

        return maxGeodes
    }

    private fun State.demand(costs: Cost) =
        costs.mapValues { (material, cost) -> cost - supply(material) }.filterValues { it >= 0 }

    private fun maxNeeded(robot: Material) = maxNeeded.getOrDefault(robot, Int.MAX_VALUE)
}

fun readBlueprints(filename: String) =
    readInput(filename)
        .map { """\d+""".toRegex().findAll(it).map(MatchResult::value).map(String::toInt).toList() }
        .map {
            Blueprint(
                it[0],
                mapOf(
                    Material.Ore to mapOf(Material.Ore to it[1]),
                    Material.Clay to mapOf(Material.Ore to it[2]),
                    Material.Obsidian to mapOf(Material.Ore to it[3], Material.Clay to it[4]),
                    Material.Geode to mapOf(Material.Ore to it[5], Material.Obsidian to it[6]),
                )
            )
        }

data class State(val materials: Map<Material, Int>, val robots: Map<Material, Int>, val time: Int) {
    val estimate =
        listOfNotNull(materials[Material.Geode], robots[Material.Geode]?.times(time)).sum()

    fun potential(blueprint: Blueprint): Int {
        val materials = materials.toMutableMap()
        val additionalRobots = mutableMapOf<Material, Int>()
        repeat(time) {
            for ((robot, count) in robots.asSequence() + additionalRobots.asSequence()) {
                materials[robot] = materials.getOrDefault(robot, 0) + count
            }
            for (robot in blueprint.affordableRobots(materials, additionalRobots)) {
                additionalRobots[robot] = additionalRobots.getOrDefault(robot, 0).inc()
            }
        }

        return materials.getOrDefault(Material.Geode, 0)
    }

    fun supply(material: Material) = materials.getOrDefault(material, 0)

    fun robots(robot: Material) = robots.getOrDefault(robot, 0)

    fun addRobot(robot: Material, costs: Cost, delta: Int) =
        State(
            materials.toMutableMap().apply {
                for ((material, count) in robots) {
                    this[material] = getOrDefault(material, 0) + count.times(delta.inc())
                }
                for ((material, cost) in costs) {
                    this[material] = getOrDefault(material, 0) - cost
                }
            },
            robots.plus(robot to robots(robot).inc()),
            time - delta - 1,
        )

    private fun Blueprint.affordableRobots(
        materials: MutableMap<Material, Int>,
        additionalRobots: MutableMap<Material, Int>
    ) =
        costs
            .filter { (robot, costs) ->
                costs.all { (material, cost) ->
                    materials.getOrDefault(material, 0) >=
                        additionalRobots.getOrDefault(robot, 0).inc() * cost
                }
            }
            .map { (robot, _) -> robot }
}

fun part1(filename: String) = readBlueprints(filename).sumOf { it.qualityLevel(24) }

private const val filename = "Day19"

fun main() {
    println(part1(filename))
}
