package com.viniciussantos.projetonetflixapi

import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.squareup.picasso.Picasso
import com.viniciussantos.projetonetflixapi.adapter.FilmeAdapter
import com.viniciussantos.projetonetflixapi.api.FilmeAPI
import com.viniciussantos.projetonetflixapi.api.RetrofitService
import com.viniciussantos.projetonetflixapi.api.ViaCepAPI
import com.viniciussantos.projetonetflixapi.databinding.ActivityMainBinding
import com.viniciussantos.projetonetflixapi.model.Endereco
import com.viniciussantos.projetonetflixapi.model.FilmeRecente
import com.viniciussantos.projetonetflixapi.model.FilmeResposta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var paginaAtual = 1
    private val TAG = "info_filme"
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val filmeAPI by lazy {
        //RetrofitService.filmeAPI
        RetrofitService.recuperarApi( FilmeAPI::class.java )
    }
    private val viaCepAPI by lazy {
        RetrofitService.recuperarViaCep()
//        RetrofitService.recuperarApi( ViaCepAPI::class.java )
    }
    var jobFilmeRecente: Job? = null
    var jobFilmesPopulares: Job? = null
    var gridLayoutManager: GridLayoutManager? = null
    private lateinit var filmeAdapter: FilmeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        inicializarViews()
        recuperarEndereco()
    }

    private fun recuperarEndereco() {
        CoroutineScope(Dispatchers.IO).launch {
            var response: Response<Endereco>? = null

            try {
                // Fazer a requisição para recuperar os filmes populares
                response = viaCepAPI.recuperarEndereco()
            } catch (e: Exception) {
                // Tratar exceção em caso de erro na requisição
                exibirMensagem("Erro ao fazer a requisição")
            }
            if (response != null) {
                if (response.isSuccessful) {
                    // Se a requisição for bem-sucedida, atualizar o RecyclerView com os filmes populares
                    val endereco = response.body()
                  if(endereco != null){
                      val logradouro = endereco.logradouro
                      val bairro = endereco.bairro
                      Log.i("viacep","recuperarEndereco: $logradouro")
                  }


                } else {
                    // Se a requisição não for bem-sucedida, exibir mensagem de erro com o código de erro
                    exibirMensagem("Problema ao fazer a requisição CODIGO: ${response.code()}")
                }
            } else {
                // Se não foi possível fazer a requisição, exibir mensagem genérica de erro
                exibirMensagem("Não foi possível fazer a requisição")
            }
        }
    }

    // Método para inicializar as views e configurar o RecyclerView
    private fun inicializarViews() {
        filmeAdapter = FilmeAdapter { filme ->
            // Ao clicar em um filme, abrir a tela de detalhes passando o ID do filme
            val intent = Intent(this, DetalhesActivity::class.java)
            intent.putExtra("filme", filme)
            startActivity(intent)
        }
        binding.rvPopulares.adapter = filmeAdapter
        gridLayoutManager = GridLayoutManager(
            this,
            2
        )



        binding.rvPopulares.layoutManager = gridLayoutManager
        // Configurar o layout do RecyclerView para exibir os filmes populares na horizontal

        binding.rvPopulares.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                /*
                                val ultimoItemVisivel = gridLayoutManager?.findLastVisibleItemPosition()
                                val totalItens = recyclerView.adapter?.itemCount
                                Log.i(
                                    "recycler_test",
                                    "ultimo: ultimoItemVisivel: $ultimoItemVisivel totalItens: $totalItens"
                                )
                                if (ultimoItemVisivel != null&& totalItens != null  ) {
                                    if (totalItens  == ultimoItemVisivel) {// chegou no ultimo item
                                        binding.fabAdicionar.hide()
                                    } else {// nao chegou no ultimo item
                                        binding.fabAdicionar.show()
                                    }
                                }
                                Log.i("recycler_test","onScrolled: dx: $dx dy: $dy")
                                if(dy>0){//descendo
                                    binding.fabAdicionar.hide()
                                }else{// subindo
                                      binding.fabAdicionar.show()
                                }
                               */
                val podeDescerVerticalmente = recyclerView.canScrollVertically(1)
                // 1) Chegar ao final da lista
                if (!podeDescerVerticalmente) {//NAO nao pode descer mais
                    // Carregar mais itens
                    Log.i("recycler_api", "paginaAtual: $paginaAtual")
                    recuperarFilmesPopularesProximaPagina()

                }
                Log.i("recycler_api", "podeDescerVerticalmente $podeDescerVerticalmente")
            }
        })

    }
//    class ScrollCustomizado: RecyclerView.OnScrollListener() {
//        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//            super.onScrolled(recyclerView, dx, dy)
//        }
//
//    }

    override fun onStart() {
        super.onStart()

        // Ao iniciar a activity, recuperar informações sobre o filme recente e filmes populares
        recuperarFilmeRecente()
        recuperarFilmesPopulares()
    }

    private fun recuperarFilmesPopularesProximaPagina() {
        if (paginaAtual < 1000) {
            paginaAtual++
            recuperarFilmesPopulares(paginaAtual)
        }
    }

    // Método para recuperar os filmes populares da API
    private fun recuperarFilmesPopulares(pagina: Int = 1) {
        jobFilmesPopulares = CoroutineScope(Dispatchers.IO).launch {
            var response: Response<FilmeResposta>? = null

            try {
                // Fazer a requisição para recuperar os filmes populares
                response = filmeAPI.recuperarFilmesPopulares(pagina)
            } catch (e: Exception) {
                // Tratar exceção em caso de erro na requisição
                exibirMensagem("Erro ao fazer a requisição")
            }
            if (response != null) {
                if (response.isSuccessful) {
                    // Se a requisição for bem-sucedida, atualizar o RecyclerView com os filmes populares
                    val filmeResposta = response.body()
                    val listaFilmes = filmeResposta?.filmes
                    if (listaFilmes != null && listaFilmes.isNotEmpty()) {
                        withContext(Dispatchers.Main) {
                            filmeAdapter.adicionarLista(listaFilmes)
                        }
                    }
                } else {
                    // Se a requisição não for bem-sucedida, exibir mensagem de erro com o código de erro
                    exibirMensagem("Problema ao fazer a requisição CODIGO: ${response.code()}")
                }
            } else {
                // Se não foi possível fazer a requisição, exibir mensagem genérica de erro
                exibirMensagem("Não foi possível fazer a requisição")
            }
        }
    }

    // Método para recuperar informações sobre o filme recente da API
    private fun recuperarFilmeRecente() {
        jobFilmeRecente = CoroutineScope(Dispatchers.IO).launch {
            var response: Response<FilmeRecente>? = null

            try {
                // Fazer a requisição para recuperar o filme recente
                response = filmeAPI.recuperarFilmeRecente()
            } catch (e: Exception) {
                // Tratar exceção em caso de erro na requisição
                exibirMensagem("Erro ao fazer a requisição")
            }
            if (response != null) {
                if (response.isSuccessful) {
                    // Se a requisição for bem-sucedida, exibir informações sobre o filme recente na tela
                    val filmeRecente = response.body()
                    val nomeImagem = filmeRecente?.poster_path
                    val titulo = filmeRecente?.title
                    val url = RetrofitService.BASE_URL_IMAGEM + "w780" + nomeImagem
                    withContext(Dispatchers.Main) {
                        // Carregar a imagem do filme recente usando Picasso e exibi-la na ImageView
                        Picasso.get()
                            .load(url)
                            .error(R.drawable.capa)
                            .into(binding.imgCapa)
                    }
                } else {
                    // Se a requisição não for bem-sucedida, exibir mensagem de erro com o código de erro
                    exibirMensagem("Problema ao fazer a requisição CODIGO: ${response.code()}")
                }
            } else {
                // Se não foi possível fazer a requisição, exibir mensagem genérica de erro
                exibirMensagem("Não foi possível fazer a requisição")
            }
        }
    }

    // Método para exibir mensagens na forma de toast
    private fun exibirMensagem(mensagem: String) {
        Toast.makeText(
            applicationContext,
            mensagem,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onStop() {
        super.onStop()
        // Ao parar a activity, cancelar os jobs de requisição
        jobFilmeRecente?.cancel()
        jobFilmesPopulares?.cancel()
    }
}
