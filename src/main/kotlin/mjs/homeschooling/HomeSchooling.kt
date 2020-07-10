package mjs.homeschooling

data class Assignment(
        val canBeSplit: Boolean,
        val childOneTasks: List<Task> = emptyList(),
        val childTwoTasks: List<Task> = emptyList(),
        val childThreeTasks: List<Task> = emptyList()
)
val noAssignment = Assignment(false)

fun divideBetweenThreeChildren(allTasks: List<Task>) = when {
        allTasks.size < 3 -> noAssignment
        sumPoints(allTasks) % 3 != 0 -> noAssignment
        else -> splitTasks(allTasks)
    }

fun splitTasks(allTasks: List<Task>): Assignment {
    val childOneTasks = mutableListOf<Task>()
    val childTwoTasks = mutableListOf<Task>()
    val childThreeTasks = mutableListOf<Task>()
    childOneTasks.add(allTasks[0])
    childTwoTasks.add(allTasks[1])
    childThreeTasks.add(allTasks[2])
    return Assignment(true, childOneTasks, childTwoTasks, childThreeTasks)
}

