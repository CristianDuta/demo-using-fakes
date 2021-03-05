package crdu.demo.repositories

import crdu.demo.datasources.DataSource
import crdu.demo.entities.Todo

class TodoRepository(private val dataSource: DataSource<Todo>) {
    fun findAll(): List<Todo> {
        return dataSource.fetchDataForCollection("todos")
    }

    fun findAllForUser(userId: Int): List<Todo> {
        return dataSource.fetchDataForCollectionInRelationTo("todos", Pair("users", userId))
    }
}
