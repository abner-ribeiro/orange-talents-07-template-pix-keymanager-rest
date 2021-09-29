package br.com.zup

import br.com.zup.cliente.GrpcClientFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito

@MicronautTest
internal class ListChaveControllerTest {

    @field:Inject
    lateinit var grpcClient: KeyManagerListaServiceGrpc.KeyManagerListaServiceBlockingStub

    @field:Inject
    @field:Client("/clientes")
    lateinit var client: HttpClient

    @Test
    fun `deve listar chaves pix`() {
        given(grpcClient.listaChave(Mockito.any())).willReturn(ListaChaveResponse
            .newBuilder()
            .build())

        // acao
        val response = client
            .toBlocking()
            .exchange(
                HttpRequest.GET<Any>("/1/keys"),
                Any::class.java
            )

        // validacao
        with(response) {
            assertEquals(HttpStatus.OK, status)
        }

    }

    @Factory
    @Replaces(factory = GrpcClientFactory::class)
    internal class Clients {
        @Singleton
        fun blockingStub() = Mockito.mock(KeyManagerListaServiceGrpc.KeyManagerListaServiceBlockingStub::class.java)
    }
}