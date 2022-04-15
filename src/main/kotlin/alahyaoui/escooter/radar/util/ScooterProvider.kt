package alahyaoui.escooter.radar.util

import alahyaoui.escooter.radar.entity.Scooter

data class ScooterProviderJson(val data: Data)

data class Data(val bikes: Array<Scooter>)