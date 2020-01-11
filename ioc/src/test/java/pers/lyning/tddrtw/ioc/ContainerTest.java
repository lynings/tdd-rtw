package pers.lyning.tddrtw.ioc;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pers.lyning.tddrtw.ioc.exception.CircularReferenceException;
import pers.lyning.tddrtw.ioc.exception.InjectException;
import pers.lyning.tddrtw.ioc.exception.RepeatedRegisteredException;
import pers.lyning.tddrtw.ioc.sample.CyclicDependencyConstructor;
import pers.lyning.tddrtw.ioc.sample.NoParameterConstructor;
import pers.lyning.tddrtw.ioc.sample.OneParameterizedConstructor;
import pers.lyning.tddrtw.ioc.sample.TwoParameterizedConstructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author lyning
 */
class ContainerTest {
    private Container ioc;

    @BeforeEach
    void setUp() {
        ioc = new Container();
    }

    @Nested
    class RegisterTest {
        @Test
        void should_contain_a_class_when_registered_a_class() throws Exception {
            // when
            ioc.register(NoParameterConstructor.class);
            // then
            assertThat(ioc.contain(NoParameterConstructor.class)).isTrue();
        }

        @Test
        void should_not_contain_a_class_when_the_class_is_not_register() throws Exception {
            // then
            assertThat(ioc.contain(NoParameterConstructor.class)).isFalse();
        }

        @Test
        void should_registry_failure_when_repeatedly_registering_the_same_class() throws Exception {
            // given
            ioc.register(NoParameterConstructor.class);
            // when
            Assert<?, ? extends Throwable> assertThatThrownBy = assertThatThrownBy(() -> ioc.register(NoParameterConstructor.class));
            // then
            assertThatThrownBy.isInstanceOf(RepeatedRegisteredException.class);
        }
    }

    @Nested
    class InjectWithNonParameterConstructorTest {

        @Test
        void should_inject_failure_when_the_class_unregistered_with_ioc() throws Exception {
            // when
            Assert<?, ? extends Throwable> assertThatThrownBy = assertThatThrownBy(() -> ioc.inject(NoParameterConstructor.class));
            // then
            assertThatThrownBy.isInstanceOf(InjectException.class);
        }

        @Test
        void should_return_a_instance_when_injected_a_non_parameter_constructor_instance() throws Exception {
            // given
            ioc.register(NoParameterConstructor.class);
            // when
            NoParameterConstructor noParameterConstructor = ioc.inject(NoParameterConstructor.class);
            // then
            assertThat(noParameterConstructor).isNotNull();
        }

        @Test
        void should_return_the_same_instance_when_the_same_non_parameter_constructor_instance_is_injected_multiple_times() throws Exception {
            // given
            ioc.register(NoParameterConstructor.class);
            // when
            NoParameterConstructor noParameterConstructor1 = ioc.inject(NoParameterConstructor.class);
            NoParameterConstructor noParameterConstructor2 = ioc.inject(NoParameterConstructor.class);
            // then
            assertThat(noParameterConstructor1).isEqualTo(noParameterConstructor2);
        }
    }

    @Nested
    class InjectWithParametersConstructorTest {
        @Test
        void should_inject_failure_when_cyclic_dependency() throws Exception {
            // given
            ioc.register(CyclicDependencyConstructor.A.class);
            ioc.register(CyclicDependencyConstructor.B.class);
            ioc.register(CyclicDependencyConstructor.C.class);
            ioc.register(CyclicDependencyConstructor.D.class);
            ioc.register(CyclicDependencyConstructor.E.class);
            ioc.register(CyclicDependencyConstructor.F.class);
            ioc.register(CyclicDependencyConstructor.class);
            // when
            Assert<?, ? extends Throwable> assertThatThrownBy = assertThatThrownBy(() -> ioc.inject(CyclicDependencyConstructor.class));
            // then
            assertThatThrownBy.isInstanceOf(CircularReferenceException.class);
        }

        @Test
        void should_return_a_instance_when_injected_a_constructor_parameter_instance() throws Exception {
            // given
            ioc.register(OneParameterizedConstructor.class);
            // when
            OneParameterizedConstructor instance = ioc.inject(OneParameterizedConstructor.class);
            // then
            assertThat(instance).isNotNull();
        }

        @Test
        void should_return_a_instance_when_injected_two_constructor_parameter_instance() throws Exception {
            // given
            ioc.register(TwoParameterizedConstructor.class);
            ioc.register(TwoParameterizedConstructor.A.class);
            ioc.register(TwoParameterizedConstructor.InnerA1.class);
            ioc.register(TwoParameterizedConstructor.InnerA2.class);
            ioc.register(TwoParameterizedConstructor.B.class);
            ioc.register(TwoParameterizedConstructor.InnerB1.class);
            ioc.register(TwoParameterizedConstructor.InnerB2.class);
            // when
            TwoParameterizedConstructor instance = ioc.inject(TwoParameterizedConstructor.class);
            // then
            assertThat(instance).isNotNull();
        }
    }
}
