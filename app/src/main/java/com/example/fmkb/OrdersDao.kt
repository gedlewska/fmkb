package com.example.fmkb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface OrdersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: Order)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllOrders(order: List<Order?>?)

    @Query("SELECT * FROM ordersTable ORDER BY orderId ASC")
    fun getAllOrders(): Flow<List<Order>>

    @Query("DELETE FROM ordersTable")
    suspend fun deleteAll()

}