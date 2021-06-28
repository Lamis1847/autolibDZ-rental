package com.sil1.autolibdz_rental.data.room

import android.app.Application

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        RoomService.context=applicationContext
    }
}