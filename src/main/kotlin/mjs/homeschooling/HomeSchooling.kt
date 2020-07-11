package mjs.homeschooling

data class Assignment(
        val canBeSplit: Boolean,
        val childOneTasks: List<Task> = emptyList(),
        val childTwoTasks: List<Task> = emptyList(),
        val childThreeTasks: List<Task> = emptyList()
)

val NO_ASSIGNMENT = Assignment(false)

data class HomeTasks(val name: String, val tasks: List<Task> = emptyList())

fun HomeTasks.add(task: Task) = HomeTasks(name, tasks + task)

data class HomeAssignments(val homeTasks: List<HomeTasks> = listOf(HomeTasks("Kim"), HomeTasks("Shane"), HomeTasks("Drew")))

fun HomeAssignments.reorder() = HomeAssignments(homeTasks.sortedBy { it.tasks.points() })

fun divideTasks(allTasks: List<Task>): Assignment {
    if (allTasks.size < 3) return NO_ASSIGNMENT

    val allPoints = allTasks.points()
    if (allPoints % 3 != 0) return NO_ASSIGNMENT

    val pointsPerChild = allPoints / 3
    val allTasksDesc = allTasks.sortedByDescending(Task::points)
    if (allTasksDesc.first().points > pointsPerChild) return NO_ASSIGNMENT

    val assignments = allocateTasks(HomeAssignments(), allTasksDesc, pointsPerChild)
    if (assignments.homeTasks.isEmpty()) return NO_ASSIGNMENT

    return Assignment(true,
            assignments.homeTasks[0].tasks,
            assignments.homeTasks[1].tasks,
            assignments.homeTasks[2].tasks
    )
}

fun allocateTasks(assignments: HomeAssignments, tasksDesc: List<Task>, pointsPerChild: Int): HomeAssignments {
    if (tasksDesc.isEmpty()) return assignments

    val firstHomeTasks = assignments.homeTasks[0].add(tasksDesc[0])
    if (firstHomeTasks.tasks.points() > pointsPerChild) return HomeAssignments(emptyList())

    val newAssignments = HomeAssignments(listOf(
            firstHomeTasks, assignments.homeTasks[1], assignments.homeTasks[2]
    )).reorder()
    return allocateTasks(newAssignments, tasksDesc.drop(1), pointsPerChild)
}