package com.example.fmkb

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class OrdersApplication : Application() {
    private val database by lazy { OrderDatabase.getDatabase(this) }
    val repository by lazy { OrderRepository(database.getOrdersDao()) }
}