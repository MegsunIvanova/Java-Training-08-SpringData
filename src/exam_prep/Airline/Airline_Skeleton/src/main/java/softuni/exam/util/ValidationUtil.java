package softuni.exam.util;

public interface ValidationUtil {

    <E> boolean isValid(E entity);

    <E> boolean isInvalid(E entity);
}
