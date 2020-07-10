package mjs.homeschooling

data class Assignment(
        val canBeSplit: Boolean,
        val childOneTasks: List<Task> = emptyList(),
        val childTwoTasks: List<Task> = emptyList(),
        val childThreeTasks: List<Task> = emptyList()
)

val NO_ASSIGNMENT = Assignment(false)

fun divideTasks(allTasks: List<Task>): Assignment {
    if (allTasks.size < 3) return NO_ASSIGNMENT

    val allPoints = sumPoints(allTasks)
    if (allPoints % 3 != 0) return NO_ASSIGNMENT

    val pointsPerChild = allPoints / 3
    val allTasksDesc = allTasks.sortedByDescending(Task::points)
    if (allTasksDesc.first().points > pointsPerChild) return NO_ASSIGNMENT

    return splitTasks(allTasksDesc, pointsPerChild)
}

fun splitTasks(allTasksDesc: List<Task>, pointsPerChild: Int): Assignment {
    var childTasks: List<MutableList<Task>> = listOf(mutableListOf(), mutableListOf(), mutableListOf())

    allTasksDesc.forEach { task ->
        childTasks[0].add(task)
        if (sumPoints(childTasks[0]) > pointsPerChild) return NO_ASSIGNMENT
        childTasks = childTasks.sortedBy { sumPoints(it) }
    }

    return Assignment(true, childTasks[0], childTasks[1], childTasks[2])
}
