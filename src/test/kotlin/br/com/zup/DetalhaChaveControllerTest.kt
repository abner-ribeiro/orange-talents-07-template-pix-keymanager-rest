package br.com.zup

import br.com.zup.cliente.GrpcClientFactory
import com.google.protobuf.Timestamp
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
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.time.LocalDateTime
import java.time.ZoneId

@MicronautTest
internal class DetalhaChaveControllerTest {

    @field:Inject
    lateinit var grpcClient: KeyManagerDetalhaServiceGrpc.KeyManagerDetalhaServiceBlockingStub

    @field:Inject
    @field:Client("/clientes")
    lateinit var client: HttpClient

    @Test
    fun `deve detalhar uma chave pix`() {
        given(grpcClient.encontraChave(Mockito.any())).willReturn(detalhaChavePixResponse())

        // acao
        val response = client
            .toBlocking()
            .exchange(
                HttpRequest.GET<Any>("/1/keys/1"),
                Any::class.java
            )
        with(response) {
            assertEquals(HttpStatus.OK, status)
            assertNotNull(body())
        }

    }

    fun detalhaChavePixResponse() : EncontraChaveResponse {
        return EncontraChaveResponse
            .newBuilder()
            .setClienteId((1).toString())
            .setPixId((1).toString())
            .setChave(EncontraChaveResponse.ChavePix.newBuilder()
                .setTipo(TipoChave.EMAIL)
                .setChave("teste@teste.com")
                .setConta(EncontraChaveResponse.ChavePix.ContaInfo.newBuilder()
                    .setTipo(TipoConta.CONTA_CORRENTE)
                    .setInstituicao("ITAU UNIBANCO")
                    .setNomeDoTitular("abner")
                    .setCpfDoTitular("067819245219")
                    .setAgencia("0001")
                    .setNumeroDaConta("0000")
                    .build())
                .setCriadaEm(LocalDateTime.now().let {
                    val dataCriacao = it.atZone(ZoneId.of("UTC")).toInstant()
                    Timestamp.newBuilder()
                        .setSeconds(dataCriacao.epochSecond)
                        .setNanos(dataCriacao.nano)
                        .build()
                })
                .build()
            ).build()
    }

    @Factory
    @Replaces(factory = GrpcClientFactory::class)
    internal class Clients {
        @Singleton
        fun blockingStub() = Mockito.mock(KeyManagerDetalhaServiceGrpc.KeyManagerDetalhaServiceBlockingStub::class.java)
    }
}