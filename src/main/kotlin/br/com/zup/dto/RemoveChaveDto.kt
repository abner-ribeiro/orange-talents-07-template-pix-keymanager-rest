package br.com.zup.dto

import br.com.zup.RemoveChaveRequest
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
data class RemoveChaveDto (
    @field: NotNull
    val pixId: Long?
        ){
    fun paraRemoveChavePixGrpc(clienteId: String): RemoveChaveRequest{
        return RemoveChaveRequest.newBuilder()
            .setClienteId(clienteId)
            .setPixId(pixId!!)
            .build()
    }
}