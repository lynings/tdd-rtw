package pers.lyning.tddrtw.ioc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author lyning
 */
class DependencyPath {
    private final List<Class<?>> dependencyPaths = new ArrayList<>();

    List<String> asReverseList() {
        List<String> dependencies = dependencyPaths.stream()
                .map(Class::getCanonicalName)
                .collect(toList());
        Collections.reverse(dependencies);
        return dependencies;
    }

    void put(Class<?> dependence) {
        dependencyPaths.add(dependence);
    }

    int size() {
        return dependencyPaths.size();
    }
}
