package mjs.homeschooling

import kotlin.random.Random

data class Task(val name: String, val points: Int)

typealias TaskList = List<Task>

fun TaskList.points() = map(Task::points).sum()

tailrec fun nameFor(num: Int, name: String = ""): String =
        if (num == 0) name
        else nameFor((num - 1) / 26, ('A' + (num - 1) % 26).toString() + name)

fun randomTasks(numTasks: Int, maxPoints: Int = 10): TaskList =
        (1..numTasks).map { Task(nameFor(it), Random.nextInt(maxPoints) + 1) }

data class ChildTasks(val name: String, val tasks: TaskList = emptyList())

fun ChildTasks.points() = tasks.points()
fun ChildTasks.add(task: Task) = ChildTasks(name, tasks + task)
