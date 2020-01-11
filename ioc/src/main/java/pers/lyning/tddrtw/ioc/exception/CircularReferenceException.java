package pers.lyning.tddrtw.ioc.exception;

/**
 * @author lyning
 */
public class CircularReferenceException extends RuntimeException {

    public CircularReferenceException(String message) {
        super(message);
    }
}
