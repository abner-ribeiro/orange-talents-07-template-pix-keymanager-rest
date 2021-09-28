package br.com.zup.cliente

import br.com.zup.KeyManagerRegistraServiceGrpc
import br.com.zup.KeyManagerRemoveServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import jakarta.inject.Singleton

@Factory
class GrpcClientFactory(@GrpcChannel("keyManager")val channel: ManagedChannel) {

    @Singleton
    fun cadastraChave() = KeyManagerRegistraServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun removeChave() = KeyManagerRemoveServiceGrpc.newBlockingStub(channel)

}