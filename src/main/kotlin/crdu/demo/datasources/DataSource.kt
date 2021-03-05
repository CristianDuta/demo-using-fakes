package crdu.demo.datasources

import crdu.demo.entities.Entity

interface DataSource<T : Entity> {
    fun <T> fetchDataForCollection(collectionName: String): List<T>
    fun fetchDataForCollectionItem(collectionName: String, itemId: Int): T
    fun <T> fetchDataForCollectionInRelationTo(collectionName: String, inRelationTo: Pair<String, Int>): List<T>
}