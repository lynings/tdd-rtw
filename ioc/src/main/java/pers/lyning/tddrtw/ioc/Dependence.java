package pers.lyning.tddrtw.ioc;

import lombok.Getter;

import java.util.List;

/**
 * @author lyning
 */
@Getter
class Dependence {
    private final List<Class<?>> dependencies;

    private final Integer layer;

    public Dependence(Integer layer, List<Class<?>> dependencies) {
        this.layer = layer;
        this.dependencies = dependencies;
    }
}
