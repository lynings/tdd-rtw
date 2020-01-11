package pers.lyning.tddrtw.ioc;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;

/**
 * @author lyning
 */
class ConstructorInjecter implements Injecter {

    private final Map<Class<?>, Instance> classToInstanceMap = new ConcurrentHashMap<>();

    @Override
    public <T> T get(Class<T> clazz) {
        if (classToInstanceMap.containsKey(clazz)) {
            return classToInstanceMap.get(clazz).value();
        }
        inject(resolveDependencies(clazz));
        return classToInstanceMap.get(clazz).value();
    }

    private Instance inject(Class<?> clazz) {
        ConstructorResolver constructorResolver = new ConstructorResolver(clazz);
        Constructible constructible = constructorResolver.resolve();
        Object[] constructorArgs = Arrays.stream(constructible.parameterTypes())
                .map(classToInstanceMap::get)
                .map(Instance::value)
                .toArray();
        return new Instance(constructible.newInstance(constructorArgs));
    }

    private void inject(List<LayerDependence> layerDependencies) {
        List<Class<?>> dependencies = layerDependencies.stream()
                .flatMap(layerDependence -> layerDependence.getDependencies().stream())
                .collect(toList());
        for (Class<?> dependence : dependencies) {
            classToInstanceMap.putIfAbsent(dependence, inject(dependence));
        }
    }

    private List<LayerDependence> resolveDependencies(Class<?> clazz) {
        DependenceResolver resolver = new DependenceResolver(clazz);
        LayerDependencies layerDependencies = resolver.resolve();
        return layerDependencies.asList()
                .stream()
                .sorted((a, b) -> b.getLayer().compareTo(a.getLayer()))
                .collect(toList());
    }
}
