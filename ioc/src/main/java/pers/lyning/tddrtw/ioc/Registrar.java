package pers.lyning.tddrtw.ioc;

/**
 * @author lyning
 */
public interface Registrar {

    boolean contains(Class<?> clazz);

    void register(Class<?> clazz);
}
