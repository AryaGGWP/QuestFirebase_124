package com.example.pertemuan15.ui.navigation

interface DestinasiNavigasi{
    val route: String
    val tirleRes: String
}

object DestinasiHome : DestinasiNavigasi{
    override val route: String = "home"
    override val tirleRes: String = "home"
}

