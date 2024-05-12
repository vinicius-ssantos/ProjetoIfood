package com.viniciussantos.projetonetflixapi.api

import com.viniciussantos.projetonetflixapi.model.FilmeRecente
import com.viniciussantos.projetonetflixapi.model.FilmeResposta
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmeAPI {

//    @GET("movie/latest?api_key=${RetrofitService.API_KEY}")
    @GET("movie/latest")
    suspend fun recuperarFilmeRecente(): Response<FilmeRecente>

//    @GET("movie/popular?api_key=${RetrofitService.API_KEY}")
    @GET("movie/popular")
    suspend fun recuperarFilmesPopulares(
        @Query("page") pagina:Int
    ): Response<FilmeResposta>

}