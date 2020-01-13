package pers.lyning.tddrtw.ioc;

import java.util.List;

/**
 * @author lyning
 */
public interface Injecter {

    <T> T get(Class<T> clazz);

    default void inject(Class<?> clazz, List<Property> properties) {
    }
}
