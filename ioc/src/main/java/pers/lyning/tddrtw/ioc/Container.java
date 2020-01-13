package pers.lyning.tddrtw.ioc;

import pers.lyning.tddrtw.ioc.exception.RepeatedRegisteredException;

import java.util.Arrays;

/**
 * @author lyning
 */
public class Container {

    private final Injecter injecter;

    public Container(Injecter injecter) {
        Registry.clear();
        this.injecter = injecter;
    }

    public boolean contain(Class<?> clazz) {
        return Registry.contain(clazz);
    }

    public <T> T get(Class<T> clazz) {
        return injecter.get(clazz);
    }

    public Container register(Class<?> clazz) {
        if (Registry.contain(clazz)) {
            throw new RepeatedRegisteredException(String.format("%s existed", clazz.toString()));
        }
        Registry.add(clazz);
        return this;
    }

    public void register(Class<?> clazz, Property... properties) {
        register(clazz);
        injecter.inject(clazz, Arrays.asList(properties));
    }
}
