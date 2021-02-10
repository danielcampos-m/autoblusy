package daw.projeto.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import daw.projeto.validation.validator.NomeUsuarioUnicoValidator;



@Constraint(validatedBy = NomeUsuarioUnicoValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Documented
public @interface NomeUsuarioUnico {

	public String message() default "Já existe um usuário com esse nome de usuário!";
	
	public Class<?>[] groups() default {};
	
	public Class<? extends Payload>[] payload() default{};

}