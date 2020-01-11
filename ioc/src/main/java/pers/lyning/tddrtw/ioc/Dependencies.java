package pers.lyning.tddrtw.ioc;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * @author lyning
 */
class Dependencies {

    public static final int TOP_LAYER = 1;

    private final Map<Integer, List<Class<?>>> layerToDependenciesMap = new HashMap<>();

    @Getter
    private final Class<?> root;

    private Dependencies(Class<?> root) {
        this.root = root;
    }

    public List<Dependence> asList() {
        return layerToDependenciesMap.entrySet()
                .stream()
                .map(entry -> new Dependence(entry.getKey(), entry.getValue()))
                .collect(toList());
    }

    public void put(Integer layer, Class<?> clazz) {
        layerToDependenciesMap.putIfAbsent(layer, new ArrayList<>());
        layerToDependenciesMap.get(layer).add(clazz);
    }

    public static Dependencies root(Class<?> root) {
        Dependencies dependencies = new Dependencies(root);
        dependencies.put(TOP_LAYER, root);
        return dependencies;
    }
}

