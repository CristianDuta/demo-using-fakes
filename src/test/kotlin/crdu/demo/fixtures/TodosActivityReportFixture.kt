package crdu.demo.fixtures

import crdu.demo.entities.Todo
import crdu.demo.handlers.TodosActivityReport

class TodosActivityReportFixture(userId: Int) {
    val todosActivityReport: TodosActivityReport = TodosActivityReport(
        total = 5,
        todos = listOf(
            Todo(
                userId = userId,
                id = 1,
                title = "delectus aut autem",
                completed = false
            ),
            Todo(
                userId = userId,
                id = 2,
                title = "quis ut nam facilis et officia qui",
                completed = false
            ),
            Todo(
                userId = userId,
                id = 3,
                title = "fugiat veniam minus",
                completed = false
            ),
            Todo(
                userId = userId,
                id = 4,
                title = "et porro tempora",
                completed = true
            ),
            Todo(
                userId = userId,
                id = 5,
                title = "laboriosam mollitia et enim quasi adipisci quia provident illum",
                completed = true
            )
        )
    )
}
