package pers.lyning.tddrtw.ioc;

import lombok.Getter;

/**
 * @author lyning
 */
@Getter
class Dependence {

    private final Integer layer;

    private final Class<?> value;

    public Dependence(Integer layer, Class<?> value) {
        this.layer = layer;
        this.value = value;
    }
}
