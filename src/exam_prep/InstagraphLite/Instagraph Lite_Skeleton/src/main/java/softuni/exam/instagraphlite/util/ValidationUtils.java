package softuni.exam.instagraphlite.util;

public interface ValidationUtils {

    <E> boolean isValid(E entity);

    <E> boolean isInvalid(E entity);
}
