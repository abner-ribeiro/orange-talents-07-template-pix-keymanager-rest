package br.com.zup

import br.com.zup.cliente.GrpcClientFactory
import br.com.zup.dto.NovaChaveDto
import br.com.zup.dto.TipoChaveRequest
import br.com.zup.dto.TipoContaRequest
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
import java.util.*

@MicronautTest
class NovaChaveControllerTest {
    @Inject
    lateinit var grpcClient: KeyManagerRegistraServiceGrpc.KeyManagerRegistraServiceBlockingStub

    @field: Inject
    @field: Client("/clientes")
    lateinit var client: HttpClient

    @Test
    fun `deve cadastrar nova chave`(){
        val request = chavePixRequest()
        val responseGrpc = chavePixResponse()

        given(grpcClient.cadastraChave(Mockito.any())).willReturn(responseGrpc)

        val response = client
            .toBlocking()
            .exchange(
                HttpRequest.POST("/1/keys", request),
                NovaChaveDto::class.java
            )

        // validacao
        with(response) {
            assertEquals(HttpStatus.CREATED, status)
        }
    }

    private fun chavePixRequest(): NovaChaveDto{
        return NovaChaveDto(
            TipoChaveRequest.EMAIL,
            "abner@teste.com",
            TipoContaRequest.CONTA_CORRENTE
        )
    }

    private fun chavePixResponse(): NovaChaveResponse {
        return NovaChaveResponse
            .newBuilder()
            .setPixId(1L)
            .build()
    }

    @Factory
    @Replaces(factory = GrpcClientFactory::class)
    internal class Clients {
        @Singleton
        fun blockingStub() = Mockito.mock(KeyManagerRegistraServiceGrpc.KeyManagerRegistraServiceBlockingStub::class.java)
    }
}