package mjs.homeschooling

import kotlin.random.Random

data class Task(val name: String, val points: Int)

fun List<Task>.points() = map(Task::points).sum()

fun randomTasks(numTasks: Int, maxPoints: Int = 10) = (0 until numTasks)
        .map { Task(('A' + it).toString(), Random.nextInt(maxPoints) + 1) }