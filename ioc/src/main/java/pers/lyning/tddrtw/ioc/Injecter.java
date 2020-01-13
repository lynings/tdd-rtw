package pers.lyning.tddrtw.ioc;

/**
 * @author lyning
 */
public interface Injecter {

    <T> T get(Class<T> clazz);
}
