package pers.lyning.tddrtw.ioc;

import java.util.Arrays;
import java.util.List;

/**
 * @author lyning
 */
class ConstructorInjecter implements Injecter {

    private final Instances instances = new Instances();

    @Override
    public <T> T get(Class<T> clazz) {
        if (instances.contain(clazz)) {
            return instances.get(clazz).value();
        }
        List<Dependence> dependencies = new DependenceResolver(clazz).resolve();
        inject(dependencies);
        return instances.get(clazz).value();
    }

    private Instance inject(Class<?> clazz) {
        Constructible constructible = new ConstructorResolver(clazz).resolve();
        Object[] constructorArgs = Arrays.stream(constructible.parameterTypes())
                .map(instances::get)
                .map(Instance::value)
                .toArray();
        return constructible.newInstance(constructorArgs);
    }

    private void inject(List<Dependence> dependencies) {
        for (Dependence dependence : dependencies) {
            Class<?> clazz = dependence.getValue();
            instances.put(clazz, inject(clazz));
        }
    }
}
