package com.example.my_kotlin_app.retrofit

import com.example.my_kotlin_app.model.Pokedex
import io.reactivex.Observable
import retrofit2.http.GET

interface IPokemonList {
    @get:GET("pokedex.json")
    val listPokemon:Observable<Pokedex>
}