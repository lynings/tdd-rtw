package pers.lyning.tddrtw.ioc;

import lombok.Getter;

/**
 * @author lyning
 */
@Getter
class Dependence {

    private final Integer layer;

    private final Type type;

    public Dependence(Integer layer, Class<?> value) {
        this.layer = layer;
        type = Type.of(value);
    }

    public Dependence(Integer layer, Type type) {
        this.layer = layer;
        this.type = type;
    }

    public Class<?> getValue() {
        return type.getClazz();
    }
}
