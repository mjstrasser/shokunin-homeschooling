package mjs.homeschooling

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int


class HomeSchooling : CliktCommand() {
    private val tasks by argument(name="tasks",
            help = "List of tasks to allocate for homeschooling"
    ).multiple()
    private val randomTasks: Int? by option("-r", "--random-tasks",
            help="Randomly create a set of tasks to allocate for homeschooling"
    ).int()

    override fun run() {
        val tasksToAllocate = selectTasks()
        echo(tasksToAllocate)
    }

    fun selectTasks() = randomTasks?.let { randomTasks(it) } ?: tasks.map(::parseTask)
}

fun main(args: Array<String>) = HomeSchooling().main(args)
