package com.example.fmkb

import android.app.Application
import com.example.fmkb.database.OrderDatabase
import com.example.fmkb.util.OrderRepository

class OrdersApplication : Application() {
    private val database by lazy { OrderDatabase.getDatabase(this) }
    val repository by lazy { OrderRepository(database.getOrdersDao()) }
}