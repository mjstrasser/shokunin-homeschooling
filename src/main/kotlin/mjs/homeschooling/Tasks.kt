package mjs.homeschooling

import kotlin.random.Random

data class Task(val name: String, val points: Int)

typealias TaskList = List<Task>

fun TaskList.points() = map(Task::points).sum()

fun randomTasks(numTasks: Int, maxPoints: Int = 10): TaskList = (0 until numTasks)
        .map { Task(('A' + it).toString(), Random.nextInt(maxPoints) + 1) }

data class ChildTasks(val name: String, val tasks: TaskList = emptyList())

fun ChildTasks.points() = tasks.points()
fun ChildTasks.add(task: Task) = ChildTasks(name, tasks + task)
