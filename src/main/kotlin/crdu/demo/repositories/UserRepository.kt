package crdu.demo.repositories

import crdu.demo.datasources.DataSource
import crdu.demo.entities.User

class UserRepository(private val dataSource: DataSource<User>) {
    fun findAll(): List<User> {
        return dataSource.fetchDataForCollection("users")
    }

    fun findOneById(userId: Int): User {
        return dataSource.fetchDataForCollectionItem("users", userId)
    }
}