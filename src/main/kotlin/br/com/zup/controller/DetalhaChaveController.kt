package br.com.zup.controller

import br.com.zup.EncontraChaveRequest
import br.com.zup.KeyManagerDetalhaServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.validation.Validated
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Validated
@Controller("/clientes/{clienteId}/keys/{pixId}")
class DetalhaChaveController(val client: KeyManagerDetalhaServiceGrpc.KeyManagerDetalhaServiceBlockingStub) {
    @Get
    fun remove(clienteId: String, pixId: String): HttpResponse<Any> {
        val response = client.encontraChave(EncontraChaveRequest.newBuilder()
            .setPixId(EncontraChaveRequest.FiltroPorPixId.newBuilder()
                .setClienteId(clienteId)
                .setPixId(pixId)
                .build())
            .build())

        val chaveResponse = object {
            val clienteId = response.clienteId
            val pixId = response.pixId
            val chave = response.chave.chave
            val criadaEm = response.chave.criadaEm.let {
                LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
            }
        }
        return HttpResponse.ok(chaveResponse)
    }
}