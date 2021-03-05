package crdu.demo.entities

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String
): Entity
