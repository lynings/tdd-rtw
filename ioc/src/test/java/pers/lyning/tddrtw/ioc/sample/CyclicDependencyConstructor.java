package pers.lyning.tddrtw.ioc.sample;

import lombok.Getter;

/**
 * 循环依赖：
 * E -> F
 * F -> E
 *
 * @author lyning
 */
@Getter
public class CyclicDependencyConstructor {

    private final A a;

    private final B b;

    public CyclicDependencyConstructor(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public static class A {
        private final C c;

        public A(C c) {
            this.c = c;
        }
    }

    public static class B {
        private final D d;

        private final E e;

        public B(D d, E e) {
            this.d = d;
            this.e = e;
        }
    }

    public static class C {
    }

    public static class D {
        private final A a;

        public D(A a) {
            this.a = a;
        }
    }

    public static class E {
        private final A a;

        private final F f;

        public E(A a, F f) {
            this.a = a;
            this.f = f;
        }
    }

    public static class F {
        private final E e;

        public F(E e) {
            this.e = e;
        }
    }
}
