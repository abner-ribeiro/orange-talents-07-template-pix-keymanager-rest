package br.com.zup.dto

import br.com.zup.NovaChaveRequest
import br.com.zup.TipoChave
import br.com.zup.TipoConta
import br.com.zup.annotation.ChavePixPattern
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
@ChavePixPattern
data class NovaChaveDto(
    @field:NotNull val tipoChave: TipoChaveRequest?,
    @field:Size(max = 77) val chave: String?,
    @field:NotNull val tipoConta: TipoContaRequest?
) {
    fun paraNovaChavePixGrpc(clienteId: String): NovaChaveRequest {
        return NovaChaveRequest.newBuilder()
            .setClienteId(clienteId)
            .setTipoChave(tipoChave?.value)
            .setChave(chave)
            .setTipoConta(tipoConta?.value)
            .build()
    }
}
enum class TipoChaveRequest(val value: TipoChave){
    CPF(TipoChave.CPF),
    TELEFONE(TipoChave.TELEFONE),
    EMAIL(TipoChave.EMAIL),
    ALEATORIA(TipoChave.ALEATORIA)
}

enum class TipoContaRequest(val value: TipoConta){
    CONTA_CORRENTE(TipoConta.CONTA_CORRENTE),
    CONTA_POUPANCA(TipoConta.CONTA_POUPANCA)
}