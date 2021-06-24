package com.example.TP1.api

import android.app.Application
import com.example.TP1.ItemToDo
import com.example.TP1.ListeToDo
import com.example.TP1.api.local.DataSource

class Repository(
        val dataSource: DataSource,
        val dataProvider: DataProvider
) {
    suspend fun getList(hash: String, id: String): List<ListeToDo> {
        return try {

            dataProvider.getListFromApi(hash).also {
                dataSource.saveLists(it as MutableList<ListeToDo>,id)
            }

        } catch (e: Exception) {
            dataSource.getLists(id)
        }
    }

    suspend fun getItem(hash: String, id: String): List<ItemToDo> {
        return try {

            dataProvider.getItems(id, hash).also {
                dataSource.saveItems(it,id)
            }

        } catch (e: Exception) {
            dataSource.getItems(id)
        }
    }

    companion object {
        fun newInstance(application: Application): Repository {
            return Repository(
                    dataSource = DataSource(application),
                    dataProvider = DataProvider()
            )
        }
    }
}