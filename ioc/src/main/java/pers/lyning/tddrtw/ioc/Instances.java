package pers.lyning.tddrtw.ioc;

import pers.lyning.tddrtw.ioc.exception.InstanceNotFountException;

import java.util.List;
import java.util.Vector;

/**
 * @author lyning
 */
public class Instances {

    private final List<Instance> instances = new Vector<>();

    public boolean contain(Class<?> clazz) {
        return instances.stream().anyMatch(instance -> instance.isInstanceOf(clazz));
    }

    public Instance get(Class<?> clazz) {
        return instances.stream()
                .filter(instance -> instance.isInstanceOf(clazz))
                .findFirst()
                .orElseThrow(() -> new InstanceNotFountException(String.format("%s not registered!", clazz.toString())));
    }

    public void put(Instance instance) {
        instances.add(instance);
    }
}
