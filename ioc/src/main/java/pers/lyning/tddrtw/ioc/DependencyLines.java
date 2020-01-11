package pers.lyning.tddrtw.ioc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author lyning
 */
class DependencyLines {
    private final List<Class<?>> dependencyLines = new ArrayList<>();

    List<String> asReverseList() {
        List<String> dependencies = dependencyLines.stream()
                .map(Class::getCanonicalName)
                .collect(toList());
        Collections.reverse(dependencies);
        return dependencies;
    }

    void put(Class<?> dependence) {
        dependencyLines.add(dependence);
    }

    int size() {
        return dependencyLines.size();
    }
}
