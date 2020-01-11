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

    public void put(Integer layer, Class<?> clazz) {
        dependencies.add(new Dependence(layer, clazz));
    }

    public static Dependencies root(Class<?> root) {
        Dependencies dependencies = new Dependencies();
        dependencies.put(TOP_LAYER, root);
        return dependencies;
    }

    public List<Dependence> values() {
        return dependencies;
    }
}

