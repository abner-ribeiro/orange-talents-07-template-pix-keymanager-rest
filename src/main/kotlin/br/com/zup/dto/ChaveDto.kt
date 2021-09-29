package br.com.zup.dto

import br.com.zup.ChaveResponse
import br.com.zup.TipoChave
import br.com.zup.TipoConta
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

data class ChaveDto(
    val pixId: String,
    val clienteId: String,
    val tipoChave: TipoChaveResponse,
    val chave: String,
    val tipoConta: TipoContaResponse,
    val criadaEm: LocalDateTime
) {

    constructor(chaveResponse: ChaveResponse) : this(
        chaveResponse.pixId,
        chaveResponse.clienteId,
        TipoChaveResponse.valueOf(chaveResponse.tipoChave.name),
        chaveResponse.chave,
        TipoContaResponse.valueOf(chaveResponse.tipoConta.name),
        chaveResponse.criadaEm.let {
            LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
        }
    ){
    }

}

enum class TipoContaResponse(val value: TipoConta) {
    CONTA_CORRENTE(TipoConta.CONTA_CORRENTE),
    CONTA_POUPANCA(TipoConta.CONTA_POUPANCA)
}

enum class TipoChaveResponse(val value: TipoChave) {
    CPF(TipoChave.CPF),
    TELEFONE(TipoChave.TELEFONE),
    EMAIL(TipoChave.EMAIL),
    ALEATORIA(TipoChave.ALEATORIA)
}

