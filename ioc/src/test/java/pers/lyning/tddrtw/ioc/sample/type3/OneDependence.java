package pers.lyning.tddrtw.ioc.sample.type3;

import lombok.Getter;

@Getter
public class OneDependence {
    private final A a;

    public OneDependence(A a) {
        this.a = a;
    }

    public static class A {
    }
}
