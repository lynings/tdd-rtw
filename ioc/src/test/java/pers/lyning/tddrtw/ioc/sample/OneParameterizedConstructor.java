package pers.lyning.tddrtw.ioc.sample;

import lombok.Getter;

@Getter
public class OneParameterizedConstructor {
    private final A a;

    public OneParameterizedConstructor(A a) {
        this.a = a;
    }

    public static class A {
    }
}
