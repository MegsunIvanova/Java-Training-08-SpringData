package softuni.exam.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.validation.Validator;

@Component
public class ValidationUtilsImpl implements ValidationUtils {

    private final Validator validator;

    public ValidationUtilsImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <E> boolean isValid(E entity) {
        return this.validator.validate(entity).isEmpty();
    }

    @Override
    public <E> boolean isInvalid(E entity) {
        return !this.isValid(entity);
    }
}
