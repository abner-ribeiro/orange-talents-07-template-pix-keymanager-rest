package br.com.zup

import br.com.zup.cliente.GrpcClientFactory
import br.com.zup.dto.NovaChaveDto
import br.com.zup.dto.RemoveChaveDto
import com.google.protobuf.Empty
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
class RemoveChaveControllerTest {
    @field:Inject
    lateinit var grpcClient: KeyManagerRemoveServiceGrpc.KeyManagerRemoveServiceBlockingStub

    @field:Inject
    @field:Client("/clientes")
    lateinit var client: HttpClient

    @Test
    fun `deve remover uma chave`() {
        val request = RemoveChaveDto(1)
        given(grpcClient.removeChave(Mockito.any())).willReturn(Empty.newBuilder().build())
        val response = client
            .toBlocking()
            .exchange(
                HttpRequest.DELETE<Any>("/1/keys", request),
                NovaChaveDto::class.java
            )
        with(response) {
            assertEquals(HttpStatus.OK, status)
        }

    }

    @Factory
    @Replaces(factory = GrpcClientFactory::class)
    internal class Clients {
        @Singleton
        fun blockingStub() = Mockito.mock(KeyManagerRemoveServiceGrpc.KeyManagerRemoveServiceBlockingStub::class.java)
    }
}