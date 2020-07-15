package mjs.homeschooling

/**
 * Assign a [TaskList] to three children with equal points, if possible.
 */
fun assignTasks(allTasks: TaskList): Assignments {
    if (allTasks.size < 3) return nope("there must be at least 3 tasks")

    val allPoints = allTasks.points()
    if (allPoints % 3 != 0) return nope("the total points ($allPoints) are not divisible by 3")

    val pointsPerChild = allPoints / 3
    val allTasksDesc = allTasks.sortedByDescending(Task::points)
    if (allTasksDesc.first().points > pointsPerChild)
        return nope("the largest task is larger than $pointsPerChild points")

    return allocateTasks(Assignments(), allTasksDesc, pointsPerChild)
}

/**
 * Allocate a task from a [TaskList] in descending order of points to an [Assignments]
 * object and fail if points cannot be divided evenly.
 */
private tailrec fun allocateTasks(assignments: Assignments, tasksDesc: TaskList, pointsPerChild: Int): Assignments {
    if (tasksDesc.isEmpty()) return assignments

    val newAssignments = assignments.assignTask(tasksDesc.first())
    if (newAssignments.childOneTasks.points() > pointsPerChild)
        return nope("the tasks (${3 * pointsPerChild} points) cannot be allocated into $pointsPerChild points each child")

    return allocateTasks(newAssignments, tasksDesc.drop(1), pointsPerChild)
}
