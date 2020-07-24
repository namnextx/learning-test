package com.osc.school.validator;

import org.apache.logging.log4j.util.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailValidatorCustom implements ConstraintValidator<Email, String> {
   private static final Pattern emailPattern = Pattern.compile("^[a-z][a-z0-9_\\.]{4,32}@(nguy|thuc|ngo)(\\.[a-z0-9]{2,4}){1,2}$");

   @Override
   public boolean isValid(String email, ConstraintValidatorContext context) {
      return Strings.isNotEmpty(email) && emailPattern.matcher(email).matches();
   }
}
