package pers.lyning.tddrtw.ioc;

import pers.lyning.tddrtw.ioc.exception.InstanceNotFountException;

import java.util.List;
import java.util.Vector;

/**
 * @author lyning
 */
public class Instances {

    private static final List<Instance> INSTANCES = new Vector<>();

    public static void clear() {
        INSTANCES.clear();
    }

    public static boolean contain(Class<?> clazz) {
        return INSTANCES.stream().anyMatch(instance -> instance.isInstanceOf(clazz));
    }

    public static Instance get(Class<?> clazz) {
        return INSTANCES.stream()
                .filter(instance -> instance.isInstanceOf(clazz))
                .findFirst()
                .orElseThrow(() -> new InstanceNotFountException(String.format("%s not registered!", clazz.toString())));
    }

    public static void put(Instance instance) {
        INSTANCES.add(instance);
    }
}
