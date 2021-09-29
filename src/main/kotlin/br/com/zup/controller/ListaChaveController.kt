package br.com.zup.controller

import br.com.zup.EncontraChaveRequest
import br.com.zup.KeyManagerDetalhaServiceGrpc
import br.com.zup.KeyManagerListaServiceGrpc
import br.com.zup.ListaChaveRequest
import br.com.zup.dto.ChaveDto
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.validation.Validated
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Validated
@Controller("/clientes/{clienteId}/keys/")
class ListaChaveController(val client: KeyManagerListaServiceGrpc.KeyManagerListaServiceBlockingStub) {
    @Get
    fun lista(clienteId: String): HttpResponse<Any> {
        val response = client.listaChave(ListaChaveRequest.newBuilder()
            .setClienteId(clienteId)
            .build())

        var chaves = mutableListOf<ChaveDto>()

        response.chavesList.forEach {
            chaves.add(ChaveDto(it))
        }
        return HttpResponse.ok(chaves)
    }
}