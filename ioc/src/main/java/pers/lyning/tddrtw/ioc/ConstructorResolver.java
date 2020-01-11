package pers.lyning.tddrtw.ioc;

import pers.lyning.tddrtw.ioc.exception.CircularReferenceException;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author lyning
 */
public class ConstructorResolver {
    private final Class<?> clazz;

    public ConstructorResolver(Class<?> clazz) {
        this.clazz = clazz;
    }

    public static void check(DependencyPath dependencyPath) {
        if (dependencyPath.size() == 1) {
            return;
        }
        List<String> dependencies = dependencyPath.asReverseList();
        for (int index = 1, size = dependencies.size(); index <= size - 1; index++) {
            if (2 * index > size) {
                break;
            }
            List<String> left = dependencies.subList(0, index);
            List<String> right = dependencies.subList(index, 2 * index);
            if (left.equals(right)) {
                throw new CircularReferenceException("cyclic dependency!");
            }
        }
    }

    public Constructible resolve() {
        return Arrays.stream(clazz.getDeclaredConstructors())
                // 拿到参数最多的构造函数
                .max(Comparator.comparingInt(Constructor::getParameterCount))
                .map(Constructible::new)
                .get();
    }
}
