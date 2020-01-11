package pers.lyning.tddrtw.ioc;

import pers.lyning.tddrtw.ioc.exception.InjectException;

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

    public <T> T inject(Class<T> clazz) {
        if (!contain(clazz)) {
            throw new InjectException(String.format("%s unregistered", clazz.toString()));
        }
        return injecter.get(clazz);
    }

    public Container register(Class<?> clazz) {
        registrar.register(clazz);
        return this;
    }
}
