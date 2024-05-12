# Projeto Netflix API

Este projeto é uma aplicação Android desenvolvida em Kotlin e Java, utilizando o Gradle como sistema de build. O objetivo é consumir a API do The Movie Database (TMDb) para exibir uma lista de filmes em um RecyclerView.

## Estrutura do Projeto

O projeto é estruturado em duas partes principais:

1. `FilmeAdapter.kt`: Este arquivo contém a implementação do adaptador para o RecyclerView que exibe a lista de filmes. Ele inclui uma classe interna `FilmeViewHolder` que é responsável por vincular os dados de um filme às visualizações do item.

2. `RetrofitService.kt`: Este arquivo contém a configuração do Retrofit para fazer chamadas de API. Ele inclui a criação de um cliente OkHttpClient com tempos de espera configurados e um interceptor de autenticação. Além disso, ele define a base URL e a chave da API para o TMDb.

## Como Usar

Para usar este projeto, você precisa ter o Android Studio instalado em seu sistema. Siga estas etapas:

1. Clone este repositório para o seu sistema local.
2. Abra o projeto no Android Studio.
3. Execute o projeto em um emulador ou dispositivo Android.

## Dependências

Este projeto usa as seguintes dependências:

- Retrofit: Para fazer chamadas de API.
- Gson: Para converter JSON em objetos Kotlin/Java.
- Picasso: Para carregar e exibir imagens da web.

## Contribuição

Contribuições são bem-vindas. Por favor, abra um problema ou faça um pull request com suas alterações.

## Licença

Este projeto é licenciado sob os termos da licença MIT.