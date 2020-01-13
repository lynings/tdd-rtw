package pers.lyning.tddrtw.ioc;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lyning
 */
class Registry {

    private static final Set<Class<?>> REGISTRIES = Collections.synchronizedSet(new HashSet<>());

    static void add(Class<?> clazz) {
        REGISTRIES.add(clazz);
    }

    static void clear() {
        REGISTRIES.clear();
    }

    static boolean contain(Class<?> clazz) {
        return REGISTRIES.contains(clazz);
    }
}
