package com.viniciussantos.projetonetflixapi

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import com.viniciussantos.projetonetflixapi.api.RetrofitService
import com.viniciussantos.projetonetflixapi.databinding.ActivityDetalhesBinding
import com.viniciussantos.projetonetflixapi.model.Filme

class DetalhesActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetalhesBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val bundle = intent.extras
        if (bundle != null) {

            val filme = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable("filme", Filme::class.java)
            } else {
                bundle.getParcelable("filme") as? Filme
            }
            if (filme != null) {
                binding.textFilmeTitulo.text = filme.title
                val nomeFilme = filme.backdrop_path
                val tamanhoFilme = "w780"
                val urlBase = RetrofitService.BASE_URL_IMAGEM
                val urlFilme = urlBase + tamanhoFilme + nomeFilme
                // Carregando a imagem do filme usando Picasso e atribuindo-a à ImageView
                Picasso.get().load(urlFilme).into(binding.imgPoster)
            }
        }
    }
}