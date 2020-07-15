package mjs.homeschooling

import com.github.ajalt.clikt.core.BadParameterValue
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int


class HomeSchooling : CliktCommand(name = "homeschooling") {
    private val tasks by argument(name = "TASKS",
            help = "List of tasks to allocate for homeschooling, e.g. A1 B2 C3 D5"
    ).multiple()
    private val randomTasks: Int? by option("-r", "--random-tasks",
            help = "Randomly create a set of tasks to allocate for homeschooling instead of a list of tasks"
    ).int()
    private val maxPoints by option("-m", "--max-points",
            help = "Maximum number of points to use in randomly-generated tasks (default 10)"
    ).int().default(10)

    override fun run() {
        val allTasks: TaskList = selectTasks()
        echo("Tasks to assign (${allTasks.points()} points): ${allTasks.summary()}")
        echo(explain(assignTasks(allTasks)))
    }

    fun selectTasks(): TaskList {
        try {
            return randomTasks?.let { generateTasks(it, maxPoints) } ?: tasks.map(::parseTask)
        } catch (e: IllegalArgumentException) {
            throw BadParameterValue(e.message!!, argument(name = "TASKS"))
        }
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
        assignments.run { "The tasks could not be assigned because $whyNot" }

    private fun tasksFor(childTasks: ChildTasks) = childTasks.name + ": " +
            childTasks.tasks.joinToString(" + ") { "Task ${it.name} (${points(it.points)})" } +
            " = ${childTasks.points()} points"

    private fun points(points: Int) = if (points == 1) "1 point" else "$points points"
}

fun main(args: Array<String>) = HomeSchooling().main(args)
