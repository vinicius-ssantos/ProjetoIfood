package com.viniciussantos.projetonetflixapi.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitService {
    companion object {
        const val API_KEY = "e5363c80535b3065e89fec033e4aa16c"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val BASE_URL_IMAGEM = "https://image.tmdb.org/t/p/"
        const val TOKEN="eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlNTM2M2M4MDUzNWIzMDY1ZTg5ZmVjMDMzZTRhYTE2YyIsInN1YiI6IjY2MTMxZTFlNmM4NDkyMDE0OWZiZjNmYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.V4aRHOnbk8Z0ZxJLL8SXTTurXDAP2UOxEReWCfQwdgo"
        private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        val filmeAPI = retrofit.create(FilmeAPI::class.java)

        fun <T> recuperarApi( classe: Class<T>) : T {
            return retrofit.create( classe )
        }

        fun recuperarViaCep(): ViaCepAPI {
            return Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(ViaCepAPI::class.java)

        }
    }
}