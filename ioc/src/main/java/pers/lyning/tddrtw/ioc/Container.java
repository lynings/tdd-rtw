package pers.lyning.tddrtw.ioc;

import pers.lyning.tddrtw.ioc.exception.RepeatedRegisteredException;

/**
 * @author lyning
 */
public class Container {

    private final Injecter injecter;

    public Container() {
        Registry.clear();
        injecter = new ConstructorInjecter();
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
}
