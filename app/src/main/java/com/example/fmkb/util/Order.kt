package com.example.fmkb.util

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ordersTable")
data class Order(
    @ColumnInfo(name = "orderId") val orderId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "modificationDate") val modificationDate: String,
    @ColumnInfo(name = "image_url") val image_url: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}