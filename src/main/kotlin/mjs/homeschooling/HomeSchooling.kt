package mjs.homeschooling

import com.github.ajalt.clikt.core.BadParameterValue
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.cooccurring
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.int
import kotlin.system.measureTimeMillis


class HomeSchooling : CliktCommand(name = "homeschooling") {

    /**
     * [OptionGroup] that ensures `--max-points` is only specified with `--random-tasks`.
     */
    class GenerateOptions : OptionGroup() {
        val taskCount: Int? by option("-r", "--random-tasks",
                help = "Randomly generate a set of tasks to allocate for homeschooling instead of a list of tasks"
        ).int().required()
        val maxPoints by option("-m", "--max-points",
                help = "Maximum number of points to use in randomly-generated tasks (default 10)"
        ).int().default(10)
    }

    private val randomTasks by GenerateOptions().cooccurring()
    private val tasks by argument(name = "TASKS",
            help = "List of tasks to allocate for homeschooling, e.g. A1 B2 C3 D5"
    ).multiple()

    override fun run() {
        val allTasks: TaskList = selectTasks()
        echo("Tasks to assign (${allTasks.points()} points): ${allTasks.summary()}")
        measureTimeMillis {
            echo(explain(assignTasks(allTasks)))
        }.also { echo("Executed in $it milliseconds") }
    }

    /**
     * Generating random tasks takes precedence over specifying tasks.
     */
    fun selectTasks() = randomTasks?.let { generateTasks(it.taskCount!!, it.maxPoints) } ?: parseTaskArgs()

    private fun parseTaskArgs() = try {
        tasks.map(::parseTask)
    } catch (e: IllegalArgumentException) {
        throw BadParameterValue(e.message!!, argument(name = "TASKS"))
    }

    private fun explain(assignments: Assignments): String {

        fun points(points: Int) = if (points == 1) "1 point" else "$points points"

        fun tasksFor(childTasks: ChildTasks) =
                childTasks.name + ": " + childTasks.tasks.joinToString(" + ") {
                    "Task ${it.name} (${points(it.points)})"
                } + " = ${childTasks.points()} points"

        return if (assignments.canBeAssigned)
            assignments.let {
                """The tasks have been assigned as follows:
            |  ${tasksFor(it.childOneTasks)}
            |  ${tasksFor(it.childTwoTasks)}
            |  ${tasksFor(it.childThreeTasks)}
            """.trimMargin()
            }
        else
            assignments.let { "The tasks could not be assigned because ${it.whyNot}" }
    }
}

fun main(args: Array<String>) = HomeSchooling().main(args)
