package pers.lyning.tddrtw.ioc.sample.type2;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lyning
 */
@Setter
@Getter
public class SetterDependence {

    private Integer age;

    private CyclicDependence cyclicDependence;

    private Dependence dependence;

    private String name;

    public static class Dependence {
    }

    @Getter
    @Setter
    public static class CyclicDependence {
        private Dependence dependence;

        private SetterDependence setterDependence;
    }
}
