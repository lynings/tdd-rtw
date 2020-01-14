package pers.lyning.tddrtw.ioc;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pers.lyning.tddrtw.ioc.exception.*;
import pers.lyning.tddrtw.ioc.sample.type2.SetterDependence;
import pers.lyning.tddrtw.ioc.sample.type3.CyclicDependency;
import pers.lyning.tddrtw.ioc.sample.type3.NoDependence;
import pers.lyning.tddrtw.ioc.sample.type3.OneDependence;
import pers.lyning.tddrtw.ioc.sample.type3.TwoDependence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author lyning
 */
class ContainerTest {
    private Container ioc;

    @Nested
    class Type3IocTest {

        @BeforeEach
        void setUp() {
            ioc = new Container(new ConstructorInjecter());
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
            void should_failure_when_A_class_not_registered() throws Exception {
                // given
                ioc.register(OneDependence.class);
                // when
                Assert<?, ? extends Throwable> assertThatThrownBy = assertThatThrownBy(() -> ioc.get(OneDependence.class));
                // then
                assertThatThrownBy.isInstanceOf(InstanceNotFountException.class);
            }

            @Test
            void should_failure_when_cyclic_dependency() throws Exception {
                // given
                ioc.register(CyclicDependency.A.class)
                        .register(CyclicDependency.B.class)
                        .register(CyclicDependency.C.class)
                        .register(CyclicDependency.D.class)
                        .register(CyclicDependency.E.class)
                        .register(CyclicDependency.F.class)
                        .register(CyclicDependency.class);
                // when
                Assert<?, ? extends Throwable> assertThatThrownBy = assertThatThrownBy(() -> ioc.get(CyclicDependency.class));
                // then
                assertThatThrownBy.isInstanceOf(CircularReferenceException.class);
            }

            @Test
            void should_return_a_instance_when_injected_a_constructor_parameter_instance() throws Exception {
                // given
                ioc.register(OneDependence.class)
                        .register(OneDependence.A.class);
                // when
                OneDependence instance = ioc.get(OneDependence.class);
                // then
                assertThat(instance).isNotNull();
            }

            @Test
            void should_return_a_instance_when_injected_two_constructor_parameter_instance() throws Exception {
                // given
                ioc.register(TwoDependence.class)
                        .register(TwoDependence.A.class)
                        .register(TwoDependence.InnerA1.class)
                        .register(TwoDependence.InnerA2.class)
                        .register(TwoDependence.B.class)
                        .register(TwoDependence.InnerB1.class)
                        .register(TwoDependence.InnerB2.class);
                // when
                TwoDependence instance = ioc.get(TwoDependence.class);
                // then
                assertThat(instance).isNotNull();
            }
        }
    }

    @Nested
    class Type2IocTest {

        @BeforeEach
        void setUp() {
            ioc = new Container(new SetterInjecter());
        }

        @Test
        void should_inject_failure_when_inject_type_of_value_not_mismatch() throws Exception {
            ioc.register(SetterDependence.class, new ValueSetterProperty<>("age", "123"));
            // when
            Assert<?, ? extends Throwable> assertThatThrownBy = assertThatThrownBy(() -> ioc.get(SetterDependence.class));
            // then
            assertThatThrownBy.isInstanceOf(SetterInjecterException.class);
        }

        @Test
        void should_inject_failure_when_type_mismatch() throws Exception {
            ioc.register(SetterDependence.class, new TypeSetterProperty<>(SetterDependence.Independent.class));
            ioc.register(SetterDependence.Independent.class);
            // when
            Assert<?, ? extends Throwable> assertThatThrownBy = assertThatThrownBy(() -> ioc.get(SetterDependence.class));
            // then
            assertThatThrownBy.isInstanceOf(MethodNotFoundException.class);
        }

        @Test
        void should_inject_failure_when_type_not_register() throws Exception {
            ioc.register(SetterDependence.class, new TypeSetterProperty<>(SetterDependence.Dependence.class));
            // when
            Assert<?, ? extends Throwable> assertThatThrownBy = assertThatThrownBy(() -> ioc.get(SetterDependence.class));
            // then
            assertThatThrownBy.isInstanceOf(UnRegisteredException.class);
        }

        @Test
        void should_inject_success_when_type_inject_with_setter_given_cyclic_dependencies() throws Exception {
            // given
            ioc.register(SetterDependence.class,
                    new TypeSetterProperty<>(SetterDependence.CyclicDependence.class),
                    new TypeSetterProperty<>(SetterDependence.Dependence.class)
            );
            ioc.register(SetterDependence.CyclicDependence.class,
                    new TypeSetterProperty<>(SetterDependence.class),
                    new TypeSetterProperty<>(SetterDependence.Dependence.class)
            );
            ioc.register(SetterDependence.Dependence.class);
            // when
            SetterDependence instance = ioc.get(SetterDependence.class);
            // then
            assertThat(instance.getCyclicDependence()).isExactlyInstanceOf(SetterDependence.CyclicDependence.class);
            assertThat(instance.getCyclicDependence().getDependence()).isEqualTo(instance.getDependence());
            assertThat(instance.getCyclicDependence().getSetterDependence()).isEqualTo(instance);
        }

        @Test
        void should_inject_success_when_use_type_setter_register() throws Exception {
            // given
            ioc.register(SetterDependence.class, new TypeSetterProperty<>(SetterDependence.Dependence.class));
            ioc.register(SetterDependence.Dependence.class);
            // when
            SetterDependence instance = ioc.get(SetterDependence.class);
            // then
            assertThat(instance.getDependence()).isExactlyInstanceOf(SetterDependence.Dependence.class);
        }

        @Test
        void should_inject_success_when_use_value_setter_register() throws Exception {
            // given
            ioc.register(SetterDependence.class,
                    new ValueSetterProperty<>("age", 20),
                    new ValueSetterProperty<>("name", "hello world!!!"),
                    new ValueSetterProperty<>("dependence", new SetterDependence.Dependence()))
            ;
            // when
            SetterDependence instance = ioc.get(SetterDependence.class);
            // then
            assertThat(instance.getAge()).isEqualTo(20);
            assertThat(instance.getName()).isEqualTo("hello world!!!");
            assertThat(instance.getDependence()).isNotNull();
        }

        @Test
        void should_return_the_same_instance_when_getting_the_same_instance_multiple_times() throws Exception {
            // given
            ioc.register(SetterDependence.class, new TypeSetterProperty<>(SetterDependence.Dependence.class));
            ioc.register(SetterDependence.Dependence.class);
            // when
            SetterDependence instance1 = ioc.get(SetterDependence.class);
            SetterDependence instance2 = ioc.get(SetterDependence.class);
            // then
            assertThat(instance1).isEqualTo(instance2);
        }
    }
}
