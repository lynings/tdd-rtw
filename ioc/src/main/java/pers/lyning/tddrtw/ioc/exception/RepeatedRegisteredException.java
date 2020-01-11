package pers.lyning.tddrtw.ioc.exception;

/**
 * @author lyning
 */
public class RepeatedRegisteredException extends RuntimeException {

    public RepeatedRegisteredException(String message) {
        super(message);
    }
}
