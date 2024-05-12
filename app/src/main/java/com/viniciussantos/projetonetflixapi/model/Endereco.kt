package com.viniciussantos.projetonetflixapi.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root


@Root(
    name="xmlcep",
    strict= false // exige exatamente todos os objetos de retorno
)
data class Endereco(
    @field: Element(name="bairro") // Atributo (campo)
    @param: Element(name="bairro") // Parametro do construtor (campo)
    val bairro: String,
    @field: Element(name="complemento") // Atributo (campo)
    @param: Element(name="complemento") // Parametro do construtor (campo)
    val complemento: String,
    @field: Element(name="localidade") // Atributo (campo)
    @param: Element(name="localidade") // Parametro do construtor (campo)
    val localidade: String,
    @field: Element(name="logradouro") // Atributo (campo)
    @param: Element(name="logradouro") // Parametro do construtor (campo)
    val logradouro: String,

)