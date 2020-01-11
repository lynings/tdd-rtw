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
class LayerDependencies {

    private final Map<Integer, List<Class<?>>> layerToDependenciesMap = new HashMap<>();

    @Getter
    private final Class<?> root;

    private LayerDependencies(Class<?> root) {
        this.root = root;
    }

    public List<LayerDependence> asList() {
        return layerToDependenciesMap.entrySet()
                .stream()
                .map(entry -> new LayerDependence(entry.getKey(), entry.getValue()))
                .collect(toList());
    }

    public void put(Integer layer, Class<?> clazz) {
        layerToDependenciesMap.putIfAbsent(layer, new ArrayList<>());
        layerToDependenciesMap.get(layer).add(clazz);
    }

    public static LayerDependencies root(Class<?> clazz) {
        return new LayerDependencies(clazz);
    }
}

