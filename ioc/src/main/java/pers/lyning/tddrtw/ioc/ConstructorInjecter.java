package pers.lyning.tddrtw.ioc;

import pers.lyning.tddrtw.ioc.exception.InstanceNotFountException;

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

    private void injectDependence(List<Dependence> dependencies) {
        for (Dependence dependence : dependencies) {
            Instance instance = dependence.getType().instance();
            Instances.put(instance);
        }
    }
}
