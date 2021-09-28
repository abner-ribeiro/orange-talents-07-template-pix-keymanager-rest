package br.com.zup.annotation

import br.com.zup.NovaChaveRequest
import br.com.zup.TipoChave
import br.com.zup.dto.NovaChaveDto
import br.com.zup.dto.TipoChaveRequest
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import jakarta.inject.Singleton
import javax.validation.Constraint
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*

@MustBeDocumented
@Target(CLASS, CONSTRUCTOR)
@Retention(RUNTIME)
@Constraint(validatedBy = [ChavePixValidator::class])
annotation class ChavePixPattern(
    val message: String = "Chave pix informada está no formato inválido"
)

@Singleton
class ChavePixValidator : ConstraintValidator<ChavePixPattern, NovaChaveDto>{
    override fun isValid(
        novaChave: NovaChaveDto?,
        annotationMetadata: AnnotationValue<ChavePixPattern>,
        context: ConstraintValidatorContext
    ): Boolean {
        if(novaChave?.chave == null)
            return true

        when(novaChave?.tipoChave){
            //cpf regex
            TipoChaveRequest.CPF -> return novaChave.chave.matches("^[0-9]{11}\$".toRegex())
            //email regex
            TipoChaveRequest.EMAIL -> return novaChave.chave.matches("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])".toRegex())
            //telefone regex
            TipoChaveRequest.TELEFONE -> return novaChave.chave.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())
            // aleatoria será gerada pelo sistema posteriormente
            TipoChaveRequest.ALEATORIA -> return true
        }

        return true
    }

}
