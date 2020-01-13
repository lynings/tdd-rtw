package pers.lyning.tddrtw.ioc;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author lyning
 */
class SetterInjecter implements Injecter {

    private final Instances instances = new Instances();

    @Override
    public <T> T get(Class<T> clazz) {
        return null;
    }

    @Override
    public void inject(Class<?> clazz, List<Property> properties) {
        Constructible constructible = new ConstructorResolver(clazz).lookupDefaultConstructor();
        Instance instance = constructible.newInstance(null);
        Method[] methods = instance.getClass().getDeclaredMethods();
        for (Property property : properties) {
            String methodName = "set" + property.name().substring(0, 1).toUpperCase() + property.name().substring(1);
            Optional<Method> methodOptional = Arrays.stream(methods)
                    .filter(o -> o.getName().equals(methodName))
                    .findFirst();
            if (methodOptional.isPresent()) {
                try {
                    methodOptional.get().invoke(instance.value(), property.value());
                } catch (Exception e) {
                    throw new SetterInjecterException(String.format("%s.%s not found!", clazz.getName(), methodName));
                }
            }
        }
    }
}
