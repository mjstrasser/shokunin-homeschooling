package mjs.homeschooling

import kotlin.random.Random

data class Task(val name: String, val points: Int)

fun sumPoints(tasks: List<Task>) = tasks.map(Task::points).sum()

fun randomTasks(numTasks: Int, maxPoints: Int = 5) = (0 until numTasks)
        .map { Task(('A' + it).toString(), Random.nextInt(maxPoints) + 1) }