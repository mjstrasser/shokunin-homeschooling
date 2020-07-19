package mjs.homeschooling

import kotlin.random.Random

data class Task(val name: String, val points: Int, val sep: String = "") {
    override fun toString() = "$name$sep$points"
}


/**
 * Parse a string into a task from name + optional separator + points.
 */
val TOKEN_REGEX = Regex("(?<name>[A-Za-z_]+)(?<sep>[^\\d]*)(?<points>\\d+)")
fun parseTask(token: String) = TOKEN_REGEX.matchEntire(token)?.run {
    Task(groups["name"]!!.value, groups["points"]!!.value.toInt(), groups["sep"]!!.value)
} ?: throw IllegalArgumentException(""""$token" does not specify a task""")

typealias TaskList = List<Task>

fun TaskList.points() = map(Task::points).sum()
fun TaskList.summary() = joinToString(", ") { it.toString() }
fun TaskList.sorted() = sortedBy(Task::points)
fun TaskList.sortedDesc() = sortedByDescending(Task::points)

/**
 * Construct task name from number: A, B, ... Y, Z, AA, AB, ... AY, AZ, BA, BB, ...
 */
tailrec fun nameFor(num: Int, name: String = ""): String =
        if (num == 0) name
        else nameFor((num - 1) / 26, ('A' + (num - 1) % 26).toString() + name)

/**
 * Generate a list of tasks of the specified size, with random numbers of points.
 */
fun generateTasks(numTasks: Int, maxPoints: Int): TaskList =
        (1..numTasks).map { Task(nameFor(it), Random.nextInt(maxPoints) + 1, "") }

/**
 * Allocation of a list of tasks to a child by name.
 */
data class ChildTasks(val name: String, val tasks: TaskList = emptyList()) {
    fun points() = tasks.points()
}
