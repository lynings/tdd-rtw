package pers.lyning.tddrtw.ioc;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import pers.lyning.tddrtw.ioc.exception.CircularReferenceException;
import pers.lyning.tddrtw.ioc.sample.type3.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author lyning
 */
class ConstructorDependenceResolverTest {

    @Test
    void should_failure_when_cyclic_dependency1() throws Exception {
        // given
        ConstructorDependenceResolver constructorDependenceResolver = new ConstructorDependenceResolver(CyclicDependency.class);
        // when
        Assert<?, ? extends Throwable> assertThatThrownBy = assertThatThrownBy(constructorDependenceResolver::resolve);
        // then
        assertThatThrownBy.isInstanceOf(CircularReferenceException.class);
    }

    @Test
    void should_failure_when_cyclic_dependency2() throws Exception {
        // given
        ConstructorDependenceResolver constructorDependenceResolver = new ConstructorDependenceResolver(CyclicDependencyA.class);
        // when
        Assert<?, ? extends Throwable> assertThatThrownBy = assertThatThrownBy(constructorDependenceResolver::resolve);
        // then
        assertThatThrownBy.isInstanceOf(CircularReferenceException.class);
    }

    @Test
    void should_return_dependencies_when_resolve_no_constructor_parameter() throws Exception {
        // given
        ConstructorDependenceResolver constructorDependenceResolver = new ConstructorDependenceResolver(NoDependence.class);
        // when
        List<Dependence> actualDependencies = constructorDependenceResolver.resolve();
        // then
        List<Dependence> expectedDependencies = new ArrayList<>();
        expectedDependencies.add(new Dependence(1, NoDependence.class));
        assertThat(actualDependencies)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedDependencies);
    }

    @Test
    void should_return_dependencies_when_resolve_one_constructor_parameter() throws Exception {
        // given
        ConstructorDependenceResolver constructorDependenceResolver = new ConstructorDependenceResolver(OneDependence.class);
        // when
        List<Dependence> actualDependencies = constructorDependenceResolver.resolve();
        // then
        List<Dependence> expectedDependencies = new ArrayList<>();
        expectedDependencies.add(new Dependence(1, OneDependence.class));
        expectedDependencies.add(new Dependence(2, OneDependence.A.class));
        assertThat(actualDependencies)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedDependencies);
    }

    @Test
    void should_return_dependencies_when_resolve_two_constructor_parameter() throws Exception {
        // given
        ConstructorDependenceResolver constructorDependenceResolver = new ConstructorDependenceResolver(TwoDependence.class);
        // when
        List<Dependence> actualDependencies = constructorDependenceResolver.resolve();
        // then
        List<Dependence> expectedDependencies = new ArrayList<>();
        expectedDependencies.add(new Dependence(1, TwoDependence.class));
        expectedDependencies.add(new Dependence(2, TwoDependence.A.class));
        expectedDependencies.add(new Dependence(2, TwoDependence.B.class));
        expectedDependencies.add(new Dependence(3, TwoDependence.InnerA1.class));
        expectedDependencies.add(new Dependence(3, TwoDependence.InnerA2.class));
        expectedDependencies.add(new Dependence(3, TwoDependence.InnerB1.class));
        expectedDependencies.add(new Dependence(3, TwoDependence.InnerB2.class));
        assertThat(actualDependencies)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedDependencies);
    }
}
