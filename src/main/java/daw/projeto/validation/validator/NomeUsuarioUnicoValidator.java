package daw.projeto.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import daw.projeto.service.UsuarioService;
import daw.projeto.validation.NomeUsuarioUnico;

public class NomeUsuarioUnicoValidator implements ConstraintValidator<NomeUsuarioUnico, String> {

	private static final Logger logger = LoggerFactory.getLogger(NomeUsuarioUnicoValidator.class);
	
	@Autowired
	private UsuarioService us;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		logger.debug("value recebido para validar {}", value);
		return value != null && !us.isNomeUsuarioJaUsado(value);
	}

}