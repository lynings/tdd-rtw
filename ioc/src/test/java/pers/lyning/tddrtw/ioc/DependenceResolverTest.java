package pers.lyning.tddrtw.ioc;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import pers.lyning.tddrtw.ioc.exception.CircularReferenceException;
import pers.lyning.tddrtw.ioc.sample.CyclicDependencyConstructor;
import pers.lyning.tddrtw.ioc.sample.NoParameterConstructor;
import pers.lyning.tddrtw.ioc.sample.OneParameterizedConstructor;
import pers.lyning.tddrtw.ioc.sample.TwoParameterizedConstructor;

import java.util.ArrayList;
import java.util.Arrays;
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
        expectedLayerDependencies.add(new Dependence(1, Arrays.asList(NoParameterConstructor.class)));
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
        expectedLayerDependencies.add(new Dependence(1, Arrays.asList(OneParameterizedConstructor.class)));
        expectedLayerDependencies.add(new Dependence(2, Arrays.asList(OneParameterizedConstructor.A.class)));
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
        expectedLayerDependencies.add(new Dependence(
                1,
                Arrays.asList(TwoParameterizedConstructor.class)
        ));
        expectedLayerDependencies.add(new Dependence(
                2,
                Arrays.asList(TwoParameterizedConstructor.A.class, TwoParameterizedConstructor.B.class)
        ));
        expectedLayerDependencies.add(new Dependence(
                3,
                Arrays.asList(TwoParameterizedConstructor.InnerA1.class,
                        TwoParameterizedConstructor.InnerA2.class,
                        TwoParameterizedConstructor.InnerB1.class,
                        TwoParameterizedConstructor.InnerB2.class)
        ));
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
