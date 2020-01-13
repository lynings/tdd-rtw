package pers.lyning.tddrtw.ioc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lyning
 */
class Dependencies {

    public static final int TOP_LAYER = 1;

    private final List<Dependence> dependencies = new ArrayList<>();

    private Dependencies() {
    }

    public boolean contain(Class<?> clazz) {
        return dependencies.stream()
                .anyMatch(dependence -> dependence.getValue().equals(clazz));
    }

    public void put(Integer layer, Class<?> clazz) {
        dependencies.add(new Dependence(layer, clazz));
    }

    public void put(Integer layer, Type type) {
        dependencies.add(new Dependence(layer, type));
    }

    public static Dependencies root(Class<?> root) {
        Dependencies dependencies = new Dependencies();
        dependencies.put(TOP_LAYER, root);
        return dependencies;
    }

    public static Dependencies root(Type root) {
        Dependencies dependencies = new Dependencies();
        dependencies.put(TOP_LAYER, root);
        return dependencies;
    }

    public List<Dependence> values() {
        return dependencies;
    }
}

