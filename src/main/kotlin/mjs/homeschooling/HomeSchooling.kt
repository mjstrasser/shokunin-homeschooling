package mjs.homeschooling

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int


class HomeSchooling : CliktCommand() {
    val tasks: String? by option("-t", "--tasks",
            help = "List of tasks to allocate for homeschooling"
    )
    val randomTasks: Int? by option("-r", "--random-tasks",
            help="Randomly create a set of tasks to allocate for homeschooling"
    ).int()

    override fun run() {
        echo("Hello")
    }
}

fun main(args: Array<String>) = HomeSchooling().main(args)

fun processTasks(randomTasks: Int?, tasks: String?) {
    val runTasks = randomTasks?.let {
        randomTasks(it)
    } ?: tasks?.let {
        parseTasks(it)
    }
    print(runTasks)
}

fun parseTasks(tasks: String): TaskList = tasks.split(",")
        .map { Task(it.take(1), it.drop(1).toInt()) }
