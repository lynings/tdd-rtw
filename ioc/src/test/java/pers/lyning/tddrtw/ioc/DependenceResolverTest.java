package pers.lyning.tddrtw.ioc;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import pers.lyning.tddrtw.ioc.exception.CircularReferenceException;
import pers.lyning.tddrtw.ioc.sample.CyclicDependencyConstructor;
import pers.lyning.tddrtw.ioc.sample.NoParameterConstructor;
import pers.lyning.tddrtw.ioc.sample.OneParameterizedConstructor;
import pers.lyning.tddrtw.ioc.sample.TwoParameterizedConstructor;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author lyning
 */
class DependenceResolverTest {

    @Test
    void resolve_no_constructor_parameter_by_layer() throws Exception {
        // given
        DependenceResolver dependenceResolver = new DependenceResolver(NoParameterConstructor.class);
        // when
        List<Dependence> actualLayerDependencies = dependenceResolver.resolve();
        // then
        List<Dependence> expectedLayerDependencies = new ArrayList<>();
        expectedLayerDependencies.add(new Dependence(1, NoParameterConstructor.class));
        assertThat(actualLayerDependencies)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedLayerDependencies);
    }

    @Test
    void resolve_one_constructor_parameter_by_layer() throws Exception {
        // given
        DependenceResolver dependenceResolver = new DependenceResolver(OneParameterizedConstructor.class);
        // when
        List<Dependence> actualLayerDependencies = dependenceResolver.resolve();
        // then
        List<Dependence> expectedLayerDependencies = new ArrayList<>();
        expectedLayerDependencies.add(new Dependence(1, OneParameterizedConstructor.class));
        expectedLayerDependencies.add(new Dependence(2, OneParameterizedConstructor.A.class));
        assertThat(actualLayerDependencies)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedLayerDependencies);
    }

    @Test
    void resolve_two_constructor_parameter_by_layer() throws Exception {
        // given
        DependenceResolver dependenceResolver = new DependenceResolver(TwoParameterizedConstructor.class);
        // when
        List<Dependence> actualLayerDependencies = dependenceResolver.resolve();
        // then
        List<Dependence> expectedLayerDependencies = new ArrayList<>();
        expectedLayerDependencies.add(new Dependence(1, TwoParameterizedConstructor.class));
        expectedLayerDependencies.add(new Dependence(2, TwoParameterizedConstructor.A.class));
        expectedLayerDependencies.add(new Dependence(2, TwoParameterizedConstructor.B.class));
        expectedLayerDependencies.add(new Dependence(3, TwoParameterizedConstructor.InnerA1.class));
        expectedLayerDependencies.add(new Dependence(3, TwoParameterizedConstructor.InnerA2.class));
        expectedLayerDependencies.add(new Dependence(3, TwoParameterizedConstructor.InnerB1.class));
        expectedLayerDependencies.add(new Dependence(3, TwoParameterizedConstructor.InnerB2.class));
        assertThat(actualLayerDependencies)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedLayerDependencies);
    }

    @Test
    void should_failure_when_cyclic_dependency() throws Exception {
        // given
        DependenceResolver dependenceResolver = new DependenceResolver(CyclicDependencyConstructor.class);
        // when
        Assert<?, ? extends Throwable> assertThatThrownBy = assertThatThrownBy(dependenceResolver::resolve);
        // then
        assertThatThrownBy.isInstanceOf(CircularReferenceException.class);
    }
}
