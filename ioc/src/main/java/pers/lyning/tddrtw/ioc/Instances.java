package pers.lyning.tddrtw.ioc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lyning
 */
public class Instances {

    private final Map<Class<?>, Instance> classToInstanceMap = new ConcurrentHashMap<>();

    public boolean contain(Class<?> clazz) {
        return classToInstanceMap.containsKey(clazz);
    }

    public Instance get(Class<?> clazz) {
        return classToInstanceMap.get(clazz);
    }

    public void put(Class<?> clazz, Instance instance) {
        classToInstanceMap.putIfAbsent(clazz, instance);
    }
}
