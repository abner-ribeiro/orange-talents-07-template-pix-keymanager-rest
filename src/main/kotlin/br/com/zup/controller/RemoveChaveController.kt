package br.com.zup.controller

import br.com.zup.KeyManagerRegistraServiceGrpc
import br.com.zup.KeyManagerRemoveServiceGrpc
import br.com.zup.dto.NovaChaveDto
import br.com.zup.dto.RemoveChaveDto
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller("/clientes/{clienteId}/keys")
class RemoveChaveController(val client: KeyManagerRemoveServiceGrpc.KeyManagerRemoveServiceBlockingStub) {
    @Delete
    fun remove(clienteId: String, @Valid @Body request: RemoveChaveDto): HttpResponse<Any> {
        val response = client.removeChave(request.paraRemoveChavePixGrpc(clienteId))
        return HttpResponse.ok()
    }
}