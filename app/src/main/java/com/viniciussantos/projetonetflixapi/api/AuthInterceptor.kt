package com.viniciussantos.projetonetflixapi.api

import okhttp3.Interceptor
import okhttp3.Response

// Classe que implementa a interface Interceptor do OkHttp
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // Método intercept() é chamado sempre que ocorre uma requisição HTTP

        // 1) Acessar a requisição
        val construtorRequisicao = chain.request().newBuilder()

        // 2) Alterar URL ou Rota da requisição
   /*     val urlAtual = chain.request().url()
        val novaUrl = urlAtual.newBuilder()

        // Adiciona um parâmetro de consulta (query parameter) à URL
        novaUrl.addQueryParameter(
            "api_key",
            RetrofitService.API_KEY
        )

        // 3) Configurar nova URL na requisição
        construtorRequisicao.url(novaUrl.build())

    */


        //utilizando Bearer Token
        val requisicao = construtorRequisicao.addHeader(
            "Authorization","Bearer ${RetrofitService.TOKEN}"
        ).build()

        // Prossegue com a nova requisição (chain.proceed) após a adição do parâmetro de consulta
//        return chain.proceed(construtorRequisicao.build()) // Response
        return chain.proceed(requisicao) // Response

    }
}
