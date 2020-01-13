package pers.lyning.tddrtw.ioc;

import pers.lyning.tddrtw.ioc.exception.InstanceNotFountException;

import java.util.Arrays;
import java.util.List;

/**
 * @author lyning
 */
class ConstructorInjecter implements Injecter {

    @Override
    public <T> T get(Class<T> clazz) {
        if (Instances.contain(clazz)) {
            return Instances.get(clazz).value();
        }
        List<Dependence> dependencies = new ConstructorDependenceResolver(clazz).resolve();
        checkRegistered(dependencies);
        injectDependence(dependencies);
        return Instances.get(clazz).value();
    }

    private void checkRegistered(List<Dependence> dependencies) {
        for (Dependence dependency : dependencies) {
            if (!Registry.contain(dependency.getValue())) {
                throw new InstanceNotFountException(String.format("%s not registered", dependency.getValue().toString()));
            }
        }
    }

    private Instance injectDependence(Dependence dependence) {
        Constructible constructible = new ConstructorResolver(dependence.getValue()).resolve();
        Object[] constructorArgs = Arrays.stream(constructible.parameterTypes())
                .map(Instances::get)
                .map(Instance::value)
                .toArray();
        return constructible.newInstance(constructorArgs);
    }

    private void injectDependence(List<Dependence> dependencies) {
        for (Dependence dependence : dependencies) {
            Instances.put(injectDependence(dependence));
        }
    }
}
