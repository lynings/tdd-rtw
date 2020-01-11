package pers.lyning.tddrtw.ioc;

import pers.lyning.tddrtw.ioc.exception.RepeatedRegisteredException;

import java.util.List;
import java.util.Vector;

/**
 * @author lyning
 */
class SimpleRegistrar implements Registrar {

    private final List<Class<?>> registries = new Vector<>();

    @Override
    public boolean contains(Class<?> clazz) {
        return registries.contains(clazz);
    }

    @Override
    public void register(Class<?> clazz) {
        if (registries.contains(clazz)) {
            throw new RepeatedRegisteredException(String.format("%s existed", clazz.toString()));
        }
        registries.add(clazz);
    }
}
