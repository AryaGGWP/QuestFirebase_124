package com.example.pertemuan15

import android.app.Application
import com.example.pertemuan15.di.MahasiswaContainer

class MahasiswaApp: Application(){
    lateinit var container: MahasiswaContainer
    override fun onCreate() {
        super.onCreate()
        container = MahasiswaContainer()
    }
}