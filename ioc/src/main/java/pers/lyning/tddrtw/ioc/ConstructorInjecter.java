package pers.lyning.tddrtw.ioc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
    public <T> T inject(Class<T> clazz) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (classToInstanceMap.containsKey(clazz)) {
            return (T) classToInstanceMap.get(clazz).value();
        }
        instanceDependencies(clazz);
        Instance instance = instance(clazz);
        classToInstanceMap.putIfAbsent(clazz, instance);
        return (T) instance.value();
    }

    private Instance instance(Class<?> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] initArgs = Arrays.stream(parameterTypes)
                .map(classToInstanceMap::get)
                .map(Instance::value)
                .toArray();
        return new Instance(constructor.newInstance(initArgs));
    }

    private void instanceDependencies(Class<?> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        List<LayerDependence> layerDependencies = resolveDependencies(clazz);
        List<Class<?>> dependencies = layerDependencies.stream()
                .flatMap(layerDependence -> layerDependence.getDependencies().stream())
                .collect(toList());
        for (Class<?> dependence : dependencies) {
            classToInstanceMap.putIfAbsent(dependence, instance(dependence));
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
