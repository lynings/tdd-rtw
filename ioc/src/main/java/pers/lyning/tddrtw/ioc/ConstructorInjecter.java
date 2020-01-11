package pers.lyning.tddrtw.ioc;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

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
        inject(new DependenceResolver(clazz).resolve());
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

    private void inject(List<Dependence> layerDependencies) {
        List<Class<?>> dependencies = layerDependencies.stream()
                .flatMap(dependence -> dependence.getDependencies().stream())
                .collect(toList());
        for (Class<?> dependence : dependencies) {
            instances.put(dependence, inject(dependence));
        }
    }
}
