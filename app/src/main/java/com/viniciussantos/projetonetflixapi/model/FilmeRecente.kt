package com.viniciussantos.projetonetflixapi.model

import com.viniciussantos.myapplication.model.Genero
import com.viniciussantos.myapplication.model.ProductionCompany
import com.viniciussantos.myapplication.model.ProductionCountry
import com.viniciussantos.myapplication.model.SpokenLanguage

data class FilmeRecente(
    val adult: Boolean,
    val backdrop_path: Any,
    val belongs_to_collection: Any,
    val budget: Int,
    val genres: List<Genero>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Int,
    val poster_path: String,
    val production_companies: List<Any>,
    val production_countries: List<Any>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<Any>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Int,
    val vote_count: Int
)