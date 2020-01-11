package pers.lyning.tddrtw.ioc;

import lombok.Getter;

import java.util.List;

/**
 * @author lyning
 */
@Getter
class LayerDependence {
    private final List<Class<?>> dependencies;

    private final Integer layer;

    public LayerDependence(Integer layer, List<Class<?>> dependencies) {
        this.layer = layer;
        this.dependencies = dependencies;
    }
}
