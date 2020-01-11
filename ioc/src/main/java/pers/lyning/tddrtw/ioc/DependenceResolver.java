package pers.lyning.tddrtw.ioc;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static pers.lyning.tddrtw.ioc.Dependencies.TOP_LAYER;

/**
 * @author lyning
 */
class DependenceResolver {

    private final Class<?> clazz;

    private final Dependencies dependencies;

    private final DependencyPath dependencyPath = new DependencyPath();

    public DependenceResolver(Class<?> clazz) {
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
     * {1: ["A"]},
     * {2: "B", "C"]}
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
}
