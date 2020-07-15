package mjs.homeschooling

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int


class HomeSchooling : CliktCommand() {
    private val tasks by argument(name = "tasks",
            help = "List of tasks to allocate for homeschooling"
    ).multiple()
    private val randomTasks: Int? by option("-r", "--random-tasks",
            help = "Randomly create a set of tasks to allocate for homeschooling"
    ).int()

    override fun run() {
        val allTasks: TaskList = selectTasks()
        echo("Tasks to assign: ${allTasks.summary()}")
        echo(explain(assignTasks(allTasks)))
    }

    private fun explain(assignments: Assignments) = if (assignments.canBeAssigned)
        assignments.run {
            """The tasks have been assigned as follows:
            |  ${tasksFor(childOneTasks)}
            |  ${tasksFor(childTwoTasks)}
            |  ${tasksFor(childThreeTasks)}
            """.trimMargin()
        }
    else
        assignments.run {
            """The tasks could not be assigned because $whyNot"""
        }

    private fun tasksFor(childTasks: ChildTasks) = childTasks.name + ": " +
            childTasks.tasks.joinToString(" + ") { "Task ${it.name} (${points(it.points)})" } +
            " = ${childTasks.points()} points"

    private fun points(points: Int) = if (points == 1) "1 point" else "$points points"

    fun selectTasks() = randomTasks?.let { randomTasks(it) } ?: tasks.map(::parseTask)
}

fun main(args: Array<String>) = HomeSchooling().main(args)
