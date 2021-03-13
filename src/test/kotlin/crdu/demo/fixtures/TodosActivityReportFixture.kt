package crdu.demo.fixtures

import crdu.demo.entities.Todo
import crdu.demo.handlers.TodosActivityReport

object TodosActivityReportFixture {
    val photosActivityReport = TodosActivityReport(
        total = 5,
        todos = generateTodos()
    )

    private fun generateTodos(): List<Todo> = listOf(
        Todo(
            userId = 1,
            id = 1,
            title = "delectus aut autem",
            completed = false
        ),
        Todo(
            userId = 1,
            id = 2,
            title = "quis ut nam facilis et officia qui",
            completed = false
        ),
        Todo(
            userId = 1,
            id = 3,
            title = "fugiat veniam minus",
            completed = false
        ),
        Todo(
            userId = 1,
            id = 4,
            title = "et porro tempora",
            completed = true
        ),
        Todo(
            userId = 1,
            id = 5,
            title = "laboriosam mollitia et enim quasi adipisci quia provident illum",
            completed = true
        )
    )
}
