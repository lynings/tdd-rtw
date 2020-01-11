package pers.lyning.tddrtw.ioc;

import pers.lyning.tddrtw.ioc.exception.InstantiationException;

import java.lang.reflect.Constructor;

/**
 * @author lyning
 */
class Constructible {
    private final Constructor<?> constructor;

    public Constructible(Constructor<?> constructor) {
        this.constructor = constructor;
    }

    public Instance newInstance(Object[] initArgs) {
        try {
            return new Instance(constructor.newInstance(initArgs));
        } catch (Exception e) {
            throw new InstantiationException("instantiation exception!");
        }
    }

    public Class<?>[] parameterTypes() {
        return constructor.getParameterTypes();
    }
}
