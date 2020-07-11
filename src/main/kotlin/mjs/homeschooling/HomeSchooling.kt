package mjs.homeschooling

data class ChildTasks(val name: String, val tasks: List<Task> = emptyList())

fun ChildTasks.points() = tasks.points()
fun ChildTasks.add(task: Task) = ChildTasks(name, tasks + task)
fun ChildTasks.compareTo(other: ChildTasks) = tasks.points().compareTo(other.tasks.points())

data class Assignments(
        val canBeAssigned: Boolean = true,
        val childOneTasks: ChildTasks = ChildTasks("Kim"),
        val childTwoTasks: ChildTasks = ChildTasks("Shane"),
        val childThreeTasks: ChildTasks = ChildTasks("Drew"),
        val whyNot: String = ""
)

fun Assignments.addTask(task: Task): Assignments {
    val sortedChildren = listOf(childOneTasks, childTwoTasks, childThreeTasks).sortedBy(ChildTasks::points)
    return Assignments(
            childOneTasks = sortedChildren[0].add(task),
            childTwoTasks = sortedChildren[1],
            childThreeTasks = sortedChildren[2]
    )
}

fun nope(whyNot: String) = Assignments(canBeAssigned = false, whyNot = whyNot)

fun divideTasks(allTasks: List<Task>): Assignments {
    if (allTasks.size < 3) return nope("You must specify at least 3 tasks")

    val allPoints = allTasks.points()
    if (allPoints % 3 != 0) return nope("The points are not divisible by 3")

    val pointsPerChild = allPoints / 3
    val allTasksDesc = allTasks.sortedByDescending(Task::points)
    if (allTasksDesc.first().points > pointsPerChild) return nope("The largest task is larger than $pointsPerChild points")

    return allocateTasks(Assignments(), allTasksDesc, pointsPerChild)
}

tailrec fun allocateTasks(assignments: Assignments, tasksDesc: List<Task>, pointsPerChild: Int): Assignments {
    if (tasksDesc.isEmpty()) return assignments
    val newAssignments = assignments.addTask(tasksDesc.first())
    if (newAssignments.childOneTasks.points() > pointsPerChild) return nope("Tasks cannot be evenly divided")
    return allocateTasks(newAssignments, tasksDesc.drop(1), pointsPerChild)
}