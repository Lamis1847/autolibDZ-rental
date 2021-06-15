package com.sil1.autolibdz_rental.data.room

import android.content.Context
import androidx.room.Room
//singeleton de room
object RoomService {
    lateinit var context:Context
    val database by lazy {
        Room.databaseBuilder(context, Database::class.java,"dbStore")
            .allowMainThreadQueries().build()

    }
}