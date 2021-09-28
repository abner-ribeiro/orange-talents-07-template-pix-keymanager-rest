package br.com.zup.controller

import br.com.zup.KeyManagerRegistraServiceGrpc
import br.com.zup.dto.NovaChaveDto
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import jakarta.inject.Inject
import javax.validation.Valid

@Validated
@Controller("/clientes/{clienteId}/keys")
class NovaChaveController(val client: KeyManagerRegistraServiceGrpc.KeyManagerRegistraServiceBlockingStub) {

    @Post
    fun cadastra(clienteId: String, @Valid @Body request: NovaChaveDto): HttpResponse<Any> {
        val response = client.cadastraChave(request.paraNovaChavePixGrpc(clienteId))
        return HttpResponse.created(HttpResponse.uri("/clientes/$clienteId/keys/${response.pixId}"))
    }
}