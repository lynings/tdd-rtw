package pers.lyning.tddrtw.ioc;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import pers.lyning.tddrtw.ioc.exception.InstantiationException;

import java.lang.reflect.Constructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConstructibleTest {

    @Test
    void should_failure_when_instantiation_parameter_incorrect() throws Exception {
        // given
        Constructor<?> constructor = Sample.class.getDeclaredConstructors()[0];
        Constructible constructible = new Constructible(constructor);
        // when
        Assert<?, ? extends Throwable> assertThatThrownBy = assertThatThrownBy(() -> constructible.newInstance(new Object[]{"123"}));
        // then
        assertThatThrownBy.isInstanceOf(InstantiationException.class);
    }

    @Test
    void should_return_instance() throws Exception {
        // given
        Constructor<?> constructor = Sample.class.getDeclaredConstructors()[0];
        Constructible constructible = new Constructible(constructor);
        // when
        Sample sample = constructible.newInstance(new Object[]{100}).value();
        // then
        assertThat(sample.count).isEqualTo(100);
    }


    static class Sample {
        private final Integer count;

        Sample(Integer count) {
            this.count = count;
        }
    }
}