package pers.lyning.tddrtw.ioc;

import pers.lyning.tddrtw.ioc.exception.InstanceNotFountException;
import pers.lyning.tddrtw.ioc.exception.RepeatedRegisteredException;

import java.util.List;
import java.util.Vector;

/**
 * @author lyning
 */
public class Container {

    private final Injecter injecter;

    private final List<Class<?>> registries = new Vector<>();

    public Container() {
        injecter = new ConstructorInjecter();
    }

    public boolean contain(Class<?> clazz) {
        return registries.contains(clazz);
    }

    public <T> T get(Class<T> clazz) {
        List<Dependence> dependencies = new DependenceResolver(clazz).resolve();
        checkRegistered(dependencies);
        return injecter.get(clazz);
    }

    public Container register(Class<?> clazz) {
        if (registries.contains(clazz)) {
            throw new RepeatedRegisteredException(String.format("%s existed", clazz.toString()));
        }
        registries.add(clazz);
        return this;
    }

    private void checkRegistered(List<Dependence> dependencies) {
        for (Dependence dependency : dependencies) {
            if (!contain(dependency.getValue())) {
                throw new InstanceNotFountException(String.format("%s not registered", dependency.getValue().toString()));
            }
        }
    }
}
