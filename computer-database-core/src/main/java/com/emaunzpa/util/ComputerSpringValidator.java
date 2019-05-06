package com.emaunzpa.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.emaunzpa.dto.ComputerDTO;

public class ComputerSpringValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return ComputerDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		
		ValidationUtils.rejectIfEmpty(errors, "name", "Computer name is empty");
		
		ComputerDTO computerTested = (ComputerDTO) object;
		
		 Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
		 Matcher introducedMatcher = pattern.matcher(computerTested.getIntroducedDate());
		 Matcher discontinuedMatcher = pattern.matcher(computerTested.getDiscontinuedDate());
		
		if ("".equals(computerTested.getIntroducedDate()) & !"".equals(computerTested.getDiscontinuedDate())) {
			errors.rejectValue("discontinuedDate", "Incoherence between dates");
			System.out.println("incoherence between dates");
		}
		
		if(!"".equals(computerTested.getIntroducedDate()) & !introducedMatcher.matches()) {
			errors.rejectValue("introducedDate", "Introduced date has wrong format");
			System.out.println("Introduced date has wrong format");
		}

		if(!"".equals(computerTested.getDiscontinuedDate()) & !discontinuedMatcher.matches()) {
			errors.rejectValue("discontinuedDate", "Discontinued date has wrong format");
			System.out.println("Discontinued date has wrong format");
		}
		
	}

}
