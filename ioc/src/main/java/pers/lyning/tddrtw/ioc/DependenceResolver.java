package pers.lyning.tddrtw.ioc;

/**
 * @author lyning
 */
public class DependenceResolver {

    private static final int TOP_LAYER = 1;

    private final Class<?> clazz;

    private final DependencyLines dependencyLines;

    private final LayerDependencies layerDependencies;

    public DependenceResolver(Class<?> clazz) {
        this.clazz = clazz;
        layerDependencies = LayerDependencies.root(clazz);
        dependencyLines = new DependencyLines();
    }

    /**
     * given:
     * A -> B
     * A -> C
     * return:
     * [
     * {1: ["A"]},
     * {2: p"B", "C"]}
     * ]
     * <p>
     * -- A       -> 1(layer)
     * B   C      -> 2(layer)
     *
     * @return
     */
    public LayerDependencies resolve() {
        depthFirstResolve(clazz, TOP_LAYER);
        return layerDependencies;
    }

    private void depthFirstResolve(Class<?> clazz, int layer) {
        Class<?>[] parameterTypes = clazz.getDeclaredConstructors()[0].getParameterTypes();
        if (parameterTypes.length == 0) {
            return;
        }
        for (Class<?> parameterType : parameterTypes) {
            dependencyLines.put(parameterType);
            CircularReferenceChecker.check(dependencyLines);
            layerDependencies.put(layer, parameterType);
            depthFirstResolve(parameterType, layer + 1);
        }
    }
}
