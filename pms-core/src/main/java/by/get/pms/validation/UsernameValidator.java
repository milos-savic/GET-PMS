package by.get.pms.validation;

import by.get.pms.dataaccess.UserAccountRepository;
import by.get.pms.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Milos.Savic on 10/18/2016.
 */
public class UsernameValidator implements ConstraintValidator<Username, String> {

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Override
	public void initialize(Username constraintAnnotation) {
		// nothing to initialize
	}

	@Override
	public boolean isValid(final String username, final ConstraintValidatorContext context) {
		UserAccount userAccountByUserName = userAccountRepository.findUserAccountByUsername(username);
		return username != null && !username.isEmpty() && userAccountByUserName == null;
	}
}
