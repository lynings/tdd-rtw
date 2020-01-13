package pers.lyning.tddrtw.ioc;

import org.junit.jupiter.api.Test;
import pers.lyning.tddrtw.ioc.sample.type2.SetterDependence;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SetterDependenceResolverTest {

    @Test
    void should_return_dependencies_when_resolve_one_dependence() throws Exception {
        // given
        Registry.add(SetterDependence.class, new TypeSetterProperty<>(SetterDependence.Dependence.class));
        Registry.add(SetterDependence.Dependence.class);
        Type type = Registry.get(SetterDependence.class);
        // when
        List<Dependence> actualDependencies = new SetterDependenceResolver(type).resolve();
        // then
        List<Dependence> expectedDependencies = Arrays.asList(
                new Dependence(1, type),
                new Dependence(2, Type.of(SetterDependence.Dependence.class))
        );
        assertThat(actualDependencies)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedDependencies);
    }
}