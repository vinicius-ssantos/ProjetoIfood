package com.viniciussantos.projetonetflixapi.api

import com.viniciussantos.projetonetflixapi.model.Endereco
import com.viniciussantos.projetonetflixapi.model.FilmeRecente
import retrofit2.Response
import retrofit2.http.GET

interface ViaCepAPI {
    @GET("01001000/xml")
    suspend fun recuperarEndereco(): Response<Endereco>
}