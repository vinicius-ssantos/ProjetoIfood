package com.viniciussantos.projetonetflixapi

//Tipos Genéricos = Types Generics
fun <T> minhaFuncao( vararg itens: T ) {
    itens.forEach { item ->
        println(item)
    }
}

class Carro<T>(anoCarro: T){

}

fun main() {
    //val carro = Carro(10.90)
    minhaFuncao("VINICIUS", "TESTE", 10, true, 10.20)
}