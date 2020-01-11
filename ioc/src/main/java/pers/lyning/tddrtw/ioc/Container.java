package pers.lyning.tddrtw.ioc;

import pers.lyning.tddrtw.ioc.exception.InstanceNotFountException;

import java.util.List;

/**
 * @author lyning
 */
public class Container {

    private final Injecter injecter;

    private final Registrar registrar;

    public Container() {
        injecter = new ConstructorInjecter();
        registrar = new SimpleRegistrar();
    }

    public boolean contain(Class<?> clazz) {
        return registrar.contains(clazz);
    }

    public <T> T get(Class<T> clazz) {
        List<Dependence> dependencies = new DependenceResolver(clazz).resolve();
        checkRegistered(dependencies);
        return injecter.get(clazz);
    }

    public Container register(Class<?> clazz) {
        registrar.register(clazz);
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
