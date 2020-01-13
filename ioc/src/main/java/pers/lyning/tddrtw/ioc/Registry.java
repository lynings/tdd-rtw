package pers.lyning.tddrtw.ioc;

import pers.lyning.tddrtw.ioc.exception.UnRegisteredException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lyning
 */
class Registry {

    private static final Set<Type> REGISTRIES = Collections.synchronizedSet(new HashSet<>());

    static void add(Class<?> clazz, Property... properties) {
        REGISTRIES.add(Type.of(clazz, properties));
    }

    static void clear() {
        REGISTRIES.clear();
    }

    static boolean contain(Class<?> clazz) {
        return REGISTRIES.contains(Type.of(clazz));
    }

    static Type get(Class<?> clazz) {
        return REGISTRIES.stream()
                .filter(o -> o.equals(Type.of(clazz)))
                .findFirst()
                .orElseThrow(() -> new UnRegisteredException(String.format("%s unregistered!", clazz.getName())));
    }
}
