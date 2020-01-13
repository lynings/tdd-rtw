package pers.lyning.tddrtw.ioc.exception;

/**
 * @author lyning
 */
public class MethodNotFoundException extends RuntimeException {
    public MethodNotFoundException(String message) {
        super(message);
    }
}
