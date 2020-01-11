package pers.lyning.tddrtw.ioc;

/**
 * @author lyning
 */
public interface Injecter {

    <T> T inject(Class<T> clazz) throws Exception;
}
