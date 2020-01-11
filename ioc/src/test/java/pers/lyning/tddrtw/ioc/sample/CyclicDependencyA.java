package pers.lyning.tddrtw.ioc.sample;

import lombok.Getter;

/**
 * 循环依赖：
 * A -> A
 *
 * @author lyning
 */
@Getter
public class CyclicDependencyA {

    private final A a;

    public CyclicDependencyA(A a) {
        this.a = a;
    }

    public static class A {
        private final A a;

        public A(A a) {
            this.a = a;
        }
    }
}
