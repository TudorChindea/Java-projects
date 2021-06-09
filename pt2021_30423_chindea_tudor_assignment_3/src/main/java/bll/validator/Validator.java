package bll.validator;

/**
 * interface that must be implemented
 * @param <T>
 */
public interface Validator<T> {
    /**
     * this method validates a parameter
     * @param t
     */
    public void validate(T t);
}

