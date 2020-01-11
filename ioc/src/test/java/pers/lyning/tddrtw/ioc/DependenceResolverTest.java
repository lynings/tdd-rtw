package pers.lyning.tddrtw.ioc;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import pers.lyning.tddrtw.ioc.exception.CircularReferenceException;
import pers.lyning.tddrtw.ioc.sample.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author lyning
 */
class DependenceResolverTest {

    @Test
    void should_failure_when_cyclic_dependency1() throws Exception {
        // given
        DependenceResolver dependenceResolver = new DependenceResolver(CyclicDependency.class);
        // when
        Assert<?, ? extends Throwable> assertThatThrownBy = assertThatThrownBy(dependenceResolver::resolve);
        // then
        assertThatThrownBy.isInstanceOf(CircularReferenceException.class);
    }

    @Test
    void should_failure_when_cyclic_dependency2() throws Exception {
        // given
        DependenceResolver dependenceResolver = new DependenceResolver(CyclicDependencyA.class);
        // when
        Assert<?, ? extends Throwable> assertThatThrownBy = assertThatThrownBy(dependenceResolver::resolve);
        // then
        assertThatThrownBy.isInstanceOf(CircularReferenceException.class);
    }

    @Test
    void should_return_dependencies_when_resolve_no_constructor_parameter() throws Exception {
        // given
        DependenceResolver dependenceResolver = new DependenceResolver(NoDependence.class);
        // when
        List<Dependence> actualLayerDependencies = dependenceResolver.resolve();
        // then
        List<Dependence> expectedLayerDependencies = new ArrayList<>();
        expectedLayerDependencies.add(new Dependence(1, NoDependence.class));
        assertThat(actualLayerDependencies)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedLayerDependencies);
    }

    @Test
    void should_return_dependencies_when_resolve_one_constructor_parameter() throws Exception {
        // given
        DependenceResolver dependenceResolver = new DependenceResolver(OneDependence.class);
        // when
        List<Dependence> actualLayerDependencies = dependenceResolver.resolve();
        // then
        List<Dependence> expectedLayerDependencies = new ArrayList<>();
        expectedLayerDependencies.add(new Dependence(1, OneDependence.class));
        expectedLayerDependencies.add(new Dependence(2, OneDependence.A.class));
        assertThat(actualLayerDependencies)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedLayerDependencies);
    }

    @Test
    void should_return_dependencies_when_resolve_two_constructor_parameter() throws Exception {
        // given
        DependenceResolver dependenceResolver = new DependenceResolver(TwoDependence.class);
        // when
        List<Dependence> actualLayerDependencies = dependenceResolver.resolve();
        // then
        List<Dependence> expectedLayerDependencies = new ArrayList<>();
        expectedLayerDependencies.add(new Dependence(1, TwoDependence.class));
        expectedLayerDependencies.add(new Dependence(2, TwoDependence.A.class));
        expectedLayerDependencies.add(new Dependence(2, TwoDependence.B.class));
        expectedLayerDependencies.add(new Dependence(3, TwoDependence.InnerA1.class));
        expectedLayerDependencies.add(new Dependence(3, TwoDependence.InnerA2.class));
        expectedLayerDependencies.add(new Dependence(3, TwoDependence.InnerB1.class));
        expectedLayerDependencies.add(new Dependence(3, TwoDependence.InnerB2.class));
        assertThat(actualLayerDependencies)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedLayerDependencies);
    }
}
