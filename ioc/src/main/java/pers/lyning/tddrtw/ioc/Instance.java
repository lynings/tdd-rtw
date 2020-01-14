package pers.lyning.tddrtw.ioc;

import pers.lyning.tddrtw.ioc.exception.InjectionException;
import pers.lyning.tddrtw.ioc.exception.MethodNotFoundException;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author lyning
 */
class Instance {
    private final Object value;

    public Instance(Object value) {
        this.value = value;
    }

    public void invokeMethod(String methodName, Object value) {
        Method method = lookupMethod(methodName);
        try {
            method.invoke(value(), value);
        } catch (Exception e) {
            throw new InjectionException(String.format("%s.%s not found!", value.getClass().getName(), methodName));
        }
    }

    public boolean isInstanceOf(Class<?> clazz) {
        return value.getClass().isAssignableFrom(clazz);
    }

    public <T> T value() {
        return (T) value;
    }

    private Method[] getMethod() {
        return value().getClass().getDeclaredMethods();
    }

    private Method lookupMethod(String method) {
        return Arrays.stream(getMethod())
                .filter(o -> o.getName().equals(method))
                .findFirst()
                .orElseThrow(() -> new MethodNotFoundException(String.format("%s.%s not found!", value.getClass().getName(), method)));
    }
}