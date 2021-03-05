package crdu.demo.entities

data class Todo(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean
): Entity
