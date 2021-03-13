package crdu.demo.handlers

import crdu.demo.entities.Todo
import crdu.demo.events.EventLogger
import crdu.demo.repositories.TodoRepository
import org.http4k.core.*
import org.http4k.format.Jackson.auto
import org.http4k.lens.Path
import org.http4k.lens.int

data class TodosActivityReport(
    val total: Int,
    val todos: List<Todo>
)

fun todosActivityReportHandler(eventLogger: EventLogger, todoRepository: TodoRepository): HttpHandler {
    return {
        val userId = Path.int().of("userId")(it)

        val todos = todoRepository.findAllForUser(userId)

        Response(Status.OK).with(Body.auto<Any>().toLens() of TodosActivityReport(todos.size, todos))
    }
}
