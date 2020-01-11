package pers.lyning.tddrtw.ioc.sample;

import lombok.Getter;

@Getter
public class TwoParameterizedConstructor {

    private final A a;

    private final B b;

    public TwoParameterizedConstructor(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public static class A {
        private final InnerA1 innerA1;

        private final InnerA2 innerA2;

        public A(InnerA1 innerA1, InnerA2 innerA2) {
            this.innerA1 = innerA1;
            this.innerA2 = innerA2;
        }

    }

    public static class B {
        private final InnerB1 innerB1;

        private final InnerB2 innerB2;

        public B(InnerB1 innerB1, InnerB2 innerB2) {
            this.innerB1 = innerB1;
            this.innerB2 = innerB2;
        }
    }

    public static class InnerA2 {}

    public static class InnerA1 {}

    public static class InnerB1 {}

    public static class InnerB2 {}
}
