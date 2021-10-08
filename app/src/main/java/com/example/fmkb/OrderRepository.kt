package com.example.fmkb

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class OrderRepository(private val ordersDao: OrdersDao) {

    val allOrders: Flow<List<Order>> = ordersDao.getAllOrders()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAllOrders(orders: List<Order?>?) {
        ordersDao.insertAllOrders(orders)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllOrders() {
        ordersDao.deleteAll()
    }

    suspend fun refreshOrders() = ApiInterface.getInstance().getOrders()
}