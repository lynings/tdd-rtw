package pers.lyning.tddrtw.ioc;

import java.util.Arrays;
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
        super.get(clazz);
        Type type = Registry.get(clazz);
        List<Dependence> dependencies = new SetterDependenceResolver(type).resolve();
        injectDependencies(dependencies);
        return Instances.get(clazz).value();
    }

    private void injectDependencies(List<Dependence> dependencies) {
        for (Dependence dependence : dependencies) {
            Type type = dependence.getType();
            if (!Instances.contain(type.getClazz())) {
                instance(type);
            }
            Instance instance = Instances.get(type.getClazz());
            for (Property property : type.getProperties()) {
                // name -> setName
                String methodName = "set" + property.name().substring(0, 1).toUpperCase() + property.name().substring(1);
                if (property instanceof ValueSetterProperty) {
                    instance.invokeMethod(methodName, property.value());
                } else if (property instanceof TypeSetterProperty) {
                    instance.invokeMethod(methodName, Instances.get((Class<?>) property.value()).value());
                }
            }
        }
    }

    private void instance(Type type) {
        Constructible constructible = new ConstructorResolver(type.getClazz()).resolve();
        Object[] constructorArgs = Arrays.stream(constructible.parameterTypes())
                .map(Instances::get)
                .map(Instance::value)
                .toArray();
        Instance instance = constructible.newInstance(constructorArgs);
        Instances.put(instance);
    }
}
