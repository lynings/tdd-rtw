package pers.lyning.tddrtw.ioc;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pers.lyning.tddrtw.ioc.exception.CircularReferenceException;
import pers.lyning.tddrtw.ioc.exception.InstanceNotFountException;
import pers.lyning.tddrtw.ioc.exception.RepeatedRegisteredException;
import pers.lyning.tddrtw.ioc.sample.CyclicDependency;
import pers.lyning.tddrtw.ioc.sample.NoDependence;
import pers.lyning.tddrtw.ioc.sample.OneDependence;
import pers.lyning.tddrtw.ioc.sample.TwoDependence;

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
            ioc.register(NoDependence.class);
            // then
            assertThat(ioc.contain(NoDependence.class)).isTrue();
        }

        @Test
        void should_not_contain_a_class_when_the_class_is_not_register() throws Exception {
            // then
            assertThat(ioc.contain(NoDependence.class)).isFalse();
        }

        @Test
        void should_registry_failure_when_repeatedly_registering_the_same_class() throws Exception {
            // given
            ioc.register(NoDependence.class);
            // when
            Assert<?, ? extends Throwable> assertThatThrownBy = assertThatThrownBy(() -> ioc.register(NoDependence.class));
            // then
            assertThatThrownBy.isInstanceOf(RepeatedRegisteredException.class);
        }
    }

    @Nested
    class InjectWithNonParameterConstructorTest {

        @Test
        void should_failure_when_the_class_unregistered_with_ioc() throws Exception {
            // when
            Assert<?, ? extends Throwable> assertThatThrownBy = assertThatThrownBy(() -> ioc.get(NoDependence.class));
            // then
            assertThatThrownBy.isInstanceOf(InstanceNotFountException.class);
        }

        @Test
        void should_return_a_instance_when_injected_a_non_parameter_constructor_instance() throws Exception {
            // given
            ioc.register(NoDependence.class);
            // when
            NoDependence noDependence = ioc.get(NoDependence.class);
            // then
            assertThat(noDependence).isNotNull();
        }

        @Test
        void should_return_the_same_instance_when_the_same_non_parameter_constructor_instance_is_injected_multiple_times() throws Exception {
            // given
            ioc.register(NoDependence.class);
            // when
            NoDependence noDependence1 = ioc.get(NoDependence.class);
            NoDependence noDependence2 = ioc.get(NoDependence.class);
            // then
            assertThat(noDependence1).isEqualTo(noDependence2);
        }
    }

    @Nested
    class InjectWithParametersConstructorTest {
        @Test
        void should_failure_when_cyclic_dependency() throws Exception {
            // given
            ioc.register(CyclicDependency.A.class);
            ioc.register(CyclicDependency.B.class);
            ioc.register(CyclicDependency.C.class);
            ioc.register(CyclicDependency.D.class);
            ioc.register(CyclicDependency.E.class);
            ioc.register(CyclicDependency.F.class);
            ioc.register(CyclicDependency.class);
            // when
            Assert<?, ? extends Throwable> assertThatThrownBy = assertThatThrownBy(() -> ioc.get(CyclicDependency.class));
            // then
            assertThatThrownBy.isInstanceOf(CircularReferenceException.class);
        }

        @Test
        void should_return_a_instance_when_injected_a_constructor_parameter_instance() throws Exception {
            // given
            ioc.register(OneDependence.class);
            // when
            OneDependence instance = ioc.get(OneDependence.class);
            // then
            assertThat(instance).isNotNull();
        }

        @Test
        void should_return_a_instance_when_injected_two_constructor_parameter_instance() throws Exception {
            // given
            ioc.register(TwoDependence.class);
            ioc.register(TwoDependence.A.class);
            ioc.register(TwoDependence.InnerA1.class);
            ioc.register(TwoDependence.InnerA2.class);
            ioc.register(TwoDependence.B.class);
            ioc.register(TwoDependence.InnerB1.class);
            ioc.register(TwoDependence.InnerB2.class);
            // when
            TwoDependence instance = ioc.get(TwoDependence.class);
            // then
            assertThat(instance).isNotNull();
        }
    }
}
