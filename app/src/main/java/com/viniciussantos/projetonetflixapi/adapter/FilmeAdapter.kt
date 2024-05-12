package com.viniciussantos.projetonetflixapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.viniciussantos.projetonetflixapi.api.RetrofitService
import com.viniciussantos.projetonetflixapi.databinding.ItemFilmeBinding
import com.viniciussantos.projetonetflixapi.model.Filme

// Definindo a classe FilmeAdapter que estende RecyclerView.Adapter
class FilmeAdapter(val onClick: (Filme) -> Unit) : RecyclerView.Adapter<FilmeAdapter.FilmeViewHolder>() {

    // Lista de filmes exibidos no RecyclerView
    private var listaFilmes = mutableListOf<Filme>()

    // Função para adicionar uma lista de filmes ao RecyclerView
    fun adicionarLista(lista: List<Filme>) {
        this.listaFilmes.addAll(lista)
        notifyDataSetChanged() // Notificar o RecyclerView que os dados foram atualizados
    }

    // Classe interna ViewHolder para armazenar e gerenciar as views dos itens de filme
    inner class FilmeViewHolder(val binding: ItemFilmeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // Função para associar os dados de um filme às views do item
        fun bind(filme: Filme) {
            // Construindo a URL da imagem do filme
            val nomeFilme = filme.backdrop_path
            val tamanhoFilme = "w780"
            val urlBase = RetrofitService.BASE_URL_IMAGEM
            val urlFilme = urlBase + tamanhoFilme + nomeFilme

            // Carregando a imagem do filme usando Picasso e atribuindo-a à ImageView
            Picasso.get().load(urlFilme).into(binding.imgItemFilme)

            // Definindo o título do filme na TextView correspondente
            binding.textTitulo.text = filme.title
            binding.clItem.setOnClickListener {
                onClick(filme)
            }
        }
    }

    // Método obrigatório para criar uma nova instância de FilmeViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {
        // Inflar o layout do item de filme usando o ItemFilmeBinding gerado pelo Data Binding
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFilmeBinding.inflate(layoutInflater, parent, false)
        return FilmeViewHolder(binding)
    }

    // Método obrigatório para associar os dados de um filme à ViewHolder
    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
        val filme = listaFilmes[position]
        holder.bind(filme)
    }

    // Método obrigatório para retornar o número total de itens na lista de filmes
    override fun getItemCount(): Int {
        return listaFilmes.size
    }
}
