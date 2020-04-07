package hr.ferit.matijasokol.digitalplanner.database

import hr.ferit.matijasokol.digitalplanner.model.Item

interface Repository {

    fun saveItem(item: Item)

    fun getAllItems(): List<Item>

    fun deleteItemById(id: Long)

    fun getItemById(id: Long): Item?

    fun updateItem(item: Item)
}