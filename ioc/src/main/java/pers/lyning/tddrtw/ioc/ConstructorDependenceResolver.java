package pers.lyning.tddrtw.ioc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static pers.lyning.tddrtw.ioc.Dependencies.TOP_LAYER;

/**
 * @author lyning
 */
class ConstructorDependenceResolver {

    private final Class<?> clazz;

    private final Dependencies dependencies;

    private final DependencyPath dependencyPath = new DependencyPath();

    public ConstructorDependenceResolver(Class<?> clazz) {
        this.clazz = clazz;
        dependencies = Dependencies.root(clazz);
        dependencyPath.put(clazz);
    }

    /**
     * given:
     * A
     * <p>
     * A -> B
     * A -> C
     * <p>
     * return:
     * [
     * {1: "A"},
     * {2: "B"}
     * {2: "C"}
     * ]
     * <p>
     * -- A       -> 1(layer)
     * B   C      -> 2(layer)
     *
     * @return
     */
    public List<Dependence> resolve() {
        depthFirstResolve(clazz, TOP_LAYER + 1);
        return dependencies.values()
                .stream()
                .sorted((a, b) -> b.getLayer().compareTo(a.getLayer()))
                .collect(toList());
    }

    private void depthFirstResolve(Class<?> clazz, int layer) {
        Constructible constructible = new ConstructorResolver(clazz).resolve();
        Class<?>[] parameterTypes = constructible.parameterTypes();
        for (Class<?> dependency : parameterTypes) {
            dependencyPath.put(dependency);
            CircularReferenceChecker.check(dependencyPath);
            dependencies.put(layer, dependency);
            depthFirstResolve(dependency, layer + 1);
        }
    }

    /**
     * @author lyning
     */
    static class DependencyPath {

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
    }
}
