package crdu.demo.repositories

import crdu.demo.datasources.DataSource
import crdu.demo.entities.User
import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.format.Jackson.auto

class UserRepository(private val dataSource: DataSource) {
    fun findOneById(userId: Int): User {
        val response = dataSource.fetch(
            Request(Method.GET, "/users/$userId")
        )

        return Body.auto<User>().toLens()(response)
    }
}