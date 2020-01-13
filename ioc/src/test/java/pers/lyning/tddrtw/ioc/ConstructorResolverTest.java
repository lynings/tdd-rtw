package pers.lyning.tddrtw.ioc;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConstructorResolverTest {

    @Test
    void should_return_constructor_with_the_most_arguments() {
        // given
        ConstructorResolver constructorResolver = new ConstructorResolver(Sample.class);
        // when
        Constructible constructible = constructorResolver.lookupMostParametersConstructor();
        // then
        assertThat(constructible.parameterTypes().length).isEqualTo(2);
    }

    static class Sample {
        private final String a;

        private final String b;

        public Sample(String a, String b) {
            this.a = a;
            this.b = b;
        }

        public Sample(String a) {
            this.a = a;
            b = "b";
        }
    }
}