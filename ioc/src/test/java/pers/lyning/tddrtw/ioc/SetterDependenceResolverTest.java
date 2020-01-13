package pers.lyning.tddrtw.ioc;

import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SetterDependenceResolverTest {

    @Test
    void should_return_dependencies_when_resolve_one_dependence() throws Exception {
        // given
        Registry.add(A.class, Arrays.asList(new TypeSetterProperty(B.class)));
        Registry.add(B.class, Arrays.asList(new TypeSetterProperty(A.class)));
        Type type = Registry.get(A.class);
        // when
        List<Dependence> actualDependencies = new SetterDependenceResolver(type).resolve();
        // then
        List<Dependence> expectedDependencies = Arrays.asList(
                new Dependence(1, type),
                new Dependence(2, Type.of(B.class, Arrays.asList(new TypeSetterProperty(A.class))))
        );
        assertThat(actualDependencies)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedDependencies);
    }

    @Setter
    public static class A {
        private B a;
    }

    @Setter
    public static class B {
        private A a;
    }

}