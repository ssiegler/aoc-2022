import day15.Sensor
import day15.countDetected
import day15.findMissingBeaconFrequency
import day15.toSensor
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

private const val filename = "Day15Example"

class Day15Test {
    @Test
    fun `reads sensor`() {
        assertEquals(
            Sensor(2 to 18, -2 to 15),
            "Sensor at x=2, y=18: closest beacon is at x=-2, y=15".toSensor()
        )
    }

    @Test
    fun part1() {
        assertEquals(26, countDetected(filename, 10))
    }

    @Test
    fun part2() {
        assertEquals(56000011, findMissingBeaconFrequency(filename, 0..20))
    }
}
