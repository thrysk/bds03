package com.devsuperior.bds03.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.devsuperior.bds03.controllers.exceptions.FieldMessage;
import com.devsuperior.bds03.dto.UserInsertDTO;
import com.devsuperior.bds03.entities.User;
import com.devsuperior.bds03.repositories.UserRepository;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> { 

	@Autowired
	private UserRepository repository;
	
	@Override
	public void initialize(UserInsertValid ann) {
	}

	@Override
	public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		User user = repository.findByEmail(dto.getEmail());
		
		if (user != null) {
			list.add(new FieldMessage("email", "E-mail já existe!"));
		}
		
		// Coloque aqui seus testes de validação, acrescentando objetos FieldMessage à
		// lista

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}