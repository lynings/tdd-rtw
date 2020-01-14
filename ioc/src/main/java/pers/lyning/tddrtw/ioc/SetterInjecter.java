package pers.lyning.tddrtw.ioc;

import java.util.List;

/**
 * @author lyning
 */
class SetterInjecter extends ConstructorInjecter {

    @Override
    public <T> T get(Class<T> clazz) {
        if (Instances.contain(clazz)) {
            return Instances.get(clazz).value();
        }
        T currentInstance = super.get(clazz);
        injectDependencies(currentInstance);
        return currentInstance;
    }

    private <T> void injectDependencies(T rootInstance) {
        Type rootType = Registry.get(rootInstance.getClass());
        List<Dependence> dependencies = new SetterDependenceResolver(rootType).resolve();
        for (Dependence dependence : dependencies) {
            Type type = dependence.getType();
            if (!Instances.contain(type.getClazz())) {
                Instances.put(type.instance());
            }
            injectWithSetter(type);
        }
    }

    private void injectWithSetter(Type type) {
        Instance instance = Instances.get(type.getClazz());
        for (Property property : type.getProperties()) {
            // name -> setName
            String methodName = "set" + property.name().substring(0, 1).toUpperCase() + property.name().substring(1);
            if (property instanceof NameSetterProperty) {
                instance.invokeMethod(methodName, property.value());
            } else if (property instanceof TypeSetterProperty) {
                instance.invokeMethod(methodName, Instances.get((Class<?>) property.value()).value());
            }
        }
    }
}