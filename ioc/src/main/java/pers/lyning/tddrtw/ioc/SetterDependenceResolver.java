package pers.lyning.tddrtw.ioc;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static pers.lyning.tddrtw.ioc.Dependencies.TOP_LAYER;

/**
 * @author lyning
 */
class SetterDependenceResolver {

    private final Dependencies dependencies;

    private final Type type;

    public SetterDependenceResolver(Type type) {
        this.type = type;
        dependencies = Dependencies.root(type);
    }

    public List<Dependence> resolve() {
        depthFirstResolve(type, TOP_LAYER + 1);
        return dependencies.values()
                .stream()
                .sorted((a, b) -> b.getLayer().compareTo(a.getLayer()))
                .collect(toList());
    }

    private void depthFirstResolve(Type type, int layer) {
        List<Property> typeSetterProperties = extractTypeSetterProperties(type);
        for (Property property : typeSetterProperties) {
            Class<?> clazz = (Class<?>) property.value();
            if (dependencies.contain(clazz)) {
                continue;
            }
            Type candidate = Registry.get(clazz);
            dependencies.put(layer, candidate);
            depthFirstResolve(candidate, layer + 1);
        }
    }

    private List<Property> extractTypeSetterProperties(Type type) {
        return type.getProperties()
                .stream()
                .filter(o -> o instanceof TypeSetterProperty)
                .collect(toList());
    }
}
