package com.example.progetto_programmazionemobile.Model

class Campo(
    num: Long,
    id_club: Long,
    sup: String,
    sport: String,
    prezzo: Float,
    riscaldamento: Boolean,
    coperto: Boolean)
{
    val n_c = num
    val id_club = id_club
    val superficie = sup
    val sport = sport
    val prezzo_ora = prezzo
    val riscaldamento = riscaldamento
    var coperto = coperto

}