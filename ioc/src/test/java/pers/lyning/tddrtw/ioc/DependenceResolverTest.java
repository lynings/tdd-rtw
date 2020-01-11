package pers.lyning.tddrtw.ioc;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import pers.lyning.tddrtw.ioc.exception.CircularReferenceException;
import pers.lyning.tddrtw.ioc.sample.CyclicDependencyConstructor;
import pers.lyning.tddrtw.ioc.sample.NoParameterConstructor;
import pers.lyning.tddrtw.ioc.sample.OneParameterizedConstructor;
import pers.lyning.tddrtw.ioc.sample.TwoParameterizedConstructor;

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
        LayerDependencies actualLayerDependencies = dependenceResolver.resolve();
        // then
        assertThat(actualLayerDependencies).usingDefaultComparator()
                .isEqualToComparingFieldByField(LayerDependencies.root(NoParameterConstructor.class));
    }

    @Test
    void resolve_one_constructor_parameter_by_layer() throws Exception {
        // given
        DependenceResolver dependenceResolver = new DependenceResolver(OneParameterizedConstructor.class);
        // when
        LayerDependencies actualLayerDependencies = dependenceResolver.resolve();
        // then
        LayerDependencies expectedLayerDependencies = LayerDependencies.root(OneParameterizedConstructor.class);
        expectedLayerDependencies.put(1, OneParameterizedConstructor.A.class);
        assertThat(actualLayerDependencies).usingDefaultComparator()
                .isEqualToComparingFieldByField(expectedLayerDependencies);
    }

    @Test
    void resolve_two_constructor_parameter_by_layer() throws Exception {
        // given
        DependenceResolver dependenceResolver = new DependenceResolver(TwoParameterizedConstructor.class);
        // when
        LayerDependencies actualLayerDependencies = dependenceResolver.resolve();
        // then
        LayerDependencies expectedLayerDependencies = LayerDependencies.root(TwoParameterizedConstructor.class);
        expectedLayerDependencies.put(1, TwoParameterizedConstructor.A.class);
        expectedLayerDependencies.put(1, TwoParameterizedConstructor.B.class);
        expectedLayerDependencies.put(2, TwoParameterizedConstructor.InnerA1.class);
        expectedLayerDependencies.put(2, TwoParameterizedConstructor.InnerA2.class);
        expectedLayerDependencies.put(2, TwoParameterizedConstructor.InnerB1.class);
        expectedLayerDependencies.put(2, TwoParameterizedConstructor.InnerB2.class);
        assertThat(actualLayerDependencies).usingRecursiveComparison()
                .isEqualTo(expectedLayerDependencies);
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
