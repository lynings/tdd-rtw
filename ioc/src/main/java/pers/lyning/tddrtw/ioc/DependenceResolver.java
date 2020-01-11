package pers.lyning.tddrtw.ioc;

import static pers.lyning.tddrtw.ioc.LayerDependencies.TOP_LAYER;

/**
 * @author lyning
 */
class DependenceResolver {

    private final Class<?> clazz;

    private final DependencyPath dependencyPath = new DependencyPath();

    private final LayerDependencies layerDependencies;

    public DependenceResolver(Class<?> clazz) {
        this.clazz = clazz;
        layerDependencies = LayerDependencies.root(clazz);
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
    public LayerDependencies resolve() {
        depthFirstResolve(clazz, TOP_LAYER + 1);
        return layerDependencies;
    }

    private void depthFirstResolve(Class<?> clazz, int layer) {
        Constructible constructible = new ConstructorResolver(clazz).resolve();
        Class<?>[] parameterTypes = constructible.parameterTypes();
        for (Class<?> dependency : parameterTypes) {
            dependencyPath.put(dependency);
            CircularReferenceChecker.check(dependencyPath);
            layerDependencies.put(layer, dependency);
            depthFirstResolve(dependency, layer + 1);
        }
    }
}
